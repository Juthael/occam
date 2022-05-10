package com.tregouet.occam.alg.builders.representations.concept_trees.impl;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.Graphs;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.builders.representations.concept_trees.ConceptTreeGrower;
import com.tregouet.occam.data.representations.concepts.ConceptType;
import com.tregouet.occam.data.representations.concepts.IComplementaryConcept;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.concepts.impl.ComplementaryConcept;
import com.tregouet.occam.data.representations.concepts.impl.IsA;
import com.tregouet.occam.io.output.LocalPaths;
import com.tregouet.partitioner.IPartitioner;
import com.tregouet.partitioner.impl.ConstrainedPartitioner;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.data.InvertedUpperSemilattice;
import com.tregouet.tree_finder.utils.Functions;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

public class IfLeafIsUniversalThenSort implements ConceptTreeGrower {
	
	private static final Comparator<IConcept> iDComparator = (x, y) -> x.iD() - y.iD();
	private InvertedUpperSemilattice<IConcept, IIsA> conceptUSL;
	
	public IfLeafIsUniversalThenSort() {
	}

	@Override
	public Set<InvertedTree<IConcept, IIsA>> apply(IConceptLattice conceptLattice, InvertedTree<IConcept, IIsA> currentTree) {
		Set<InvertedTree<IConcept, IIsA>> expandedTrees = new HashSet<>();
		if (currentTree == null) {
			Set<InvertedTree<IConcept, IIsA>> initial = new HashSet<>();
			initial.add(initialTree(conceptLattice));
			return initial;
		}
		conceptUSL = conceptLattice.getOntologicalUpperSemilattice();
		Set<IConcept> leaves = currentTree.getLeaves();
		List<IConcept> unsorted = new ArrayList<>();
		for (IConcept leaf : leaves) {
			if (leaf.type() != ConceptType.PARTICULAR)
				unsorted.add(leaf);
		}
		for (IConcept genus : unsorted) {
			//build search space
			Set<IComplementaryConcept> complementaryUpperBounds = getComplementaryStrictUpperBounds(currentTree, genus);
			List<IConcept> sortedParticulars = getSortedParticulars(genus, complementaryUpperBounds);
			Set<IConcept> particulars = new HashSet<>(sortedParticulars);
			InvertedUpperSemilattice<IConcept, IIsA> searchSpace = buildSearchUSL(genus, particulars, complementaryUpperBounds);
			Map<List<IConcept>, IConcept> closedSubsetsOfParticulars2Supremum = 
					mapClosedSubsetsOfParticulars2Supremum(searchSpace, particulars);
			//classify genus extent
			List<List<IConcept>> genusSortings = 
					classify(genus, sortedParticulars, closedSubsetsOfParticulars2Supremum);
			for (List<IConcept> speciesSet : genusSortings) {
				DirectedAcyclicGraph<IConcept, IIsA> treeDAG = new DirectedAcyclicGraph<>(null, IsA::new, false);
				Graphs.addAllVertices(treeDAG, currentTree.vertexSet());
				Graphs.addAllEdges(treeDAG, currentTree, currentTree.edgeSet());
				Graphs.addAllVertices(treeDAG, speciesSet);
				for (IConcept species : speciesSet)
					treeDAG.addEdge(species, genus);
				expandedTrees.add(asInvertedTree(treeDAG, currentTree.getRoot()));
			}
			//dichotomize genus extent
			List<Pair<IConcept, IComplementaryConcept>> dichotomies = 
					dichotomize(genus, sortedParticulars, closedSubsetsOfParticulars2Supremum, searchSpace);
			for (Pair<IConcept, IComplementaryConcept> dichotomy : dichotomies) {
				DirectedAcyclicGraph<IConcept, IIsA> treeDAG = new DirectedAcyclicGraph<>(null, IsA::new, false);
				Graphs.addAllVertices(treeDAG, currentTree.vertexSet());
				Graphs.addAllEdges(treeDAG, currentTree, currentTree.edgeSet());			
				IConcept unidimensionalSpecies = dichotomy.getFirst();
				treeDAG.addVertex(unidimensionalSpecies);
				treeDAG.addEdge(unidimensionalSpecies, genus);
				IConcept complementarySpecies = dichotomy.getSecond();
				treeDAG.addVertex(complementarySpecies);
				treeDAG.addEdge(complementarySpecies, genus);
				expandedTrees.add(asInvertedTree(treeDAG, currentTree.getRoot()));
			}
		}
		return expandedTrees;
	}
	
	private static Set<IComplementaryConcept> getComplementaryStrictUpperBounds(
			DirectedAcyclicGraph<IConcept, IIsA> currentTree, IConcept genus){
		Set<IComplementaryConcept> complementarySUB = new HashSet<>();
		Set<IConcept> sUBs = currentTree.getDescendants(genus);
		for (IConcept sUB : sUBs)
			if (sUB.isComplementary())
				complementarySUB.add((IComplementaryConcept)sUB);
		return complementarySUB;
	}
	
	private static List<List<IConcept>> classify(IConcept genus, List<IConcept> sortedParticulars, 
			Map<List<IConcept>, IConcept> closedSubsetsOfParticulars2Supremum) {
		List<List<IConcept>> genusSortings = new ArrayList<>();
		List<List<List<IConcept>>> partitionsOfParticulars;
		Set<List<IConcept>> closedSubsetsOfParticulars = closedSubsetsOfParticulars2Supremum.keySet();
		IPartitioner<IConcept> partitioner = 
				new ConstrainedPartitioner<>(sortedParticulars, closedSubsetsOfParticulars, null);
		partitionsOfParticulars = partitioner.getAllPartitions();
		for (List<List<IConcept>> partition : partitionsOfParticulars) {
			List<IConcept> species = new ArrayList<>();
			for (List<IConcept> subset : partition)
				species.add(closedSubsetsOfParticulars2Supremum.get(subset));
			genusSortings.add(species);
		}
		return genusSortings;
	}
	
	private static List<Pair<IConcept, IComplementaryConcept>> dichotomize(IConcept genus, 
			List<IConcept> sortedParticulars, Map<List<IConcept>, IConcept> closedSubsetsOfParticulars2Supremum, 
			InvertedUpperSemilattice<IConcept, IIsA> searchUSL) {
		List<Pair<IConcept, IComplementaryConcept>> dichotomies = new ArrayList<>();
		IPartitioner<IConcept> maxSize2Partitioner = new ConstrainedPartitioner<>(sortedParticulars, null, 2);
		List<List<List<IConcept>>> size2Partitions = maxSize2Partitioner.getAllPartitions()
				.stream()
				.filter(p -> p.size() == 2)
				.collect(Collectors.toList());
		for (List<List<IConcept>> size2Partition : size2Partitions) {
			IConcept c = null;
			List<IConcept> cParticulars = null;
			List<IConcept> nonCParticulars = null;
			List<IConcept> firstPartition = size2Partition.get(0);
			List<IConcept> secondPartition = size2Partition.get(1);
			IConcept mapValue = closedSubsetsOfParticulars2Supremum.get(firstPartition);
			if (mapValue != null) {
				c = mapValue;
				cParticulars = firstPartition;
			}
			else {
				nonCParticulars = firstPartition;
			}
			mapValue = closedSubsetsOfParticulars2Supremum.get(secondPartition);
			if (mapValue != null) {
				if (c == null) {
					c = mapValue;
					cParticulars = secondPartition;
				}
			}
			else if (nonCParticulars == null) {
				nonCParticulars = secondPartition;
			}
			if (cParticulars != null && nonCParticulars != null) {
				IComplementaryConcept complementary;				
				IConcept nonCSupremum = Functions.supremum(searchUSL, new HashSet<>(nonCParticulars));
				if (genus.equals(nonCSupremum)) {
					Set<IContextObject> compExtent = new HashSet<>();
					for (IConcept particular : nonCParticulars)
						compExtent.addAll(particular.getExtent());
					complementary = new ComplementaryConcept(c, genus, compExtent);
				}
				else complementary = new ComplementaryConcept(c, nonCSupremum);
				dichotomies.add(new Pair<>(c, complementary));
			}
		}
		return dichotomies; 
	}
	
	private static InvertedTree<IConcept, IIsA> initialTree(IConceptLattice conceptLattice) {
		DirectedAcyclicGraph<IConcept, IIsA> initialGraph = new DirectedAcyclicGraph<>(null, IsA::new, false);
		IConcept ontologicalCommitment = conceptLattice.getOntologicalCommitment();
		IConcept truism = conceptLattice.getTruism();
		IIsA initialEdge = conceptLattice.getOntologicalUpperSemilattice().getEdge(truism, ontologicalCommitment);
		initialGraph.addVertex(truism);
		initialGraph.addVertex(ontologicalCommitment);
		initialGraph.addEdge(truism, ontologicalCommitment, initialEdge);
		Set<IConcept> leaves = new HashSet<>(Arrays.asList(new IConcept[] {truism}));
		List<IConcept> topoOrderedSet = new ArrayList<>(Arrays.asList(new IConcept[] {truism, ontologicalCommitment}));
		InvertedTree<IConcept, IIsA> initialTree = 
				new InvertedTree<IConcept, IIsA>(initialGraph, ontologicalCommitment, leaves, topoOrderedSet); 
		return initialTree;
	}	
	

	
	private InvertedUpperSemilattice<IConcept, IIsA> buildSearchUSL(IConcept genus, Set<IConcept> particulars, 
			Set<IComplementaryConcept> complementaryStrictUpperBounds) {
		DirectedAcyclicGraph<IConcept, IIsA> searchSpace = new DirectedAcyclicGraph<>(null, IsA::new, false);
		List<IConcept> topoOrder = new ArrayList<>();
		if (genus.isComplementary()) {
			IComplementaryConcept complementaryGenus = (IComplementaryConcept) genus;
			//build search space without genus
			if (complementaryGenus.getWrappedComplementing() == null) {
				IConcept supremumOfParticulars = Functions.supremum(conceptUSL, particulars);
				Set<IConcept> supremumLowerSet = Functions.lowerSet(conceptUSL, supremumOfParticulars);
				Set<IConcept> unionOfParticularUpperSets = new HashSet<>();
				for (IConcept particular : particulars)
					unionOfParticularUpperSets.addAll(Functions.upperSet(conceptUSL, particular));
				Set<IConcept> searchSpaceSet = new HashSet<>(Sets.intersection(supremumLowerSet, unionOfParticularUpperSets));
				Graphs.addAllVertices(searchSpace, searchSpaceSet);
				for (IIsA isA : conceptUSL.edgeSet()) {
					IConcept source = conceptUSL.getEdgeSource(isA);
					IConcept target = conceptUSL.getEdgeTarget(isA);
					if (searchSpaceSet.contains(source) && searchSpaceSet.contains(target))
						searchSpace.addEdge(source, target, isA);
				}
			}
			else {
				Set<IConcept> genusLowerSet = Functions.lowerSet(conceptUSL, complementaryGenus.getWrappedComplementing());
				Graphs.addAllVertices(searchSpace, genusLowerSet);
				for (IIsA isA : conceptUSL.edgeSet()) {
					IConcept target = conceptUSL.getEdgeTarget(isA); 
					if (genusLowerSet.contains(target)) //then it contains edge source
						searchSpace.addEdge(conceptUSL.getEdgeSource(isA), target, isA);
				}
			}
			//remove concepts discarded by upper bound complementary concepts
			for (IComplementaryConcept complementarySUB : complementaryStrictUpperBounds) {
				IConcept complemented = complementarySUB.getComplemented();
				searchSpace.removeAllVertices(conceptUSL.getAncestors(complemented));
			}
			//make atomistic
			makeAtomistic(searchSpace);
			//replace search space root by genus
			IConcept searchSpaceRoot = getRoot(searchSpace);
			List<IConcept> rootPredecessors = new ArrayList<>();
			for (IIsA incomingEdge : searchSpace.incomingEdgesOf(searchSpaceRoot))
				rootPredecessors.add(searchSpace.getEdgeSource(incomingEdge));
			searchSpace.removeVertex(searchSpaceRoot);
			searchSpace.addVertex(genus);
			for (IConcept predecessor : rootPredecessors)
				searchSpace.addEdge(predecessor, genus);
			
		}
		else {
			Set<IConcept> genusLowerSet = Functions.lowerSet(conceptUSL, genus);
			Graphs.addAllVertices(searchSpace, genusLowerSet);
			for (IIsA isA : conceptUSL.edgeSet()) {
				IConcept target = conceptUSL.getEdgeTarget(isA); 
				if (genusLowerSet.contains(target)) //then it contains edge source
					searchSpace.addEdge(conceptUSL.getEdgeSource(isA), target, isA);
			}
			//remove concepts discarded by upper bound complementary concepts
			for (IComplementaryConcept complementarySUB : complementaryStrictUpperBounds) {
				IConcept complemented = complementarySUB.getComplemented();
				searchSpace.removeAllVertices(conceptUSL.getAncestors(complemented));
			}
			//make atomistic
			makeAtomistic(searchSpace);
		}
		//populate topo order
		new TopologicalOrderIterator<>(searchSpace).forEachRemaining(topoOrder::add);
		//instantiate and return
		InvertedUpperSemilattice<IConcept, IIsA> searchUSL = 
				new InvertedUpperSemilattice<IConcept, IIsA>(searchSpace, genus, particulars, topoOrder);
		return searchUSL;
	}
	
	private static void makeAtomistic(DirectedAcyclicGraph<IConcept, IIsA> searchSpace) {
		List<IConcept> topoOrder = new ArrayList<>();
		Set<IConcept> leaves = getLeaves(searchSpace);
		List<Set<IConcept>> lowerSets = new ArrayList<>();
		List<Set<IConcept>> lowerBoundLeaves = new ArrayList<>();
		new TopologicalOrderIterator<>(searchSpace).forEachRemaining(topoOrder::add);
		for (int i = 0 ; i < topoOrder.size() ; i++) {
			IConcept iConcept = topoOrder.get(i);
			Set<IConcept> iLowerSet = Functions.lowerSet(searchSpace, iConcept);
			Set<IConcept> iLeaves = new HashSet<>(Sets.intersection(iLowerSet, leaves));
			if (lowerBoundLeaves.contains(iLeaves)) {
				topoOrder.remove(i);
				Functions.removeVertexAndPreserveConnectivity(searchSpace, iConcept);
				i--;
			}
			else {
				lowerSets.add(iLowerSet);
				lowerBoundLeaves.add(iLeaves);
			}
		}
	}
	
	private static Set<IConcept> getLeaves(DirectedAcyclicGraph<IConcept, IIsA> dag){
		Set<IConcept> leaves = new HashSet<>();
		for (IConcept concept : dag) {
			if (dag.inDegreeOf(concept) == 0)
				leaves.add(concept);
		}
		return leaves;
	}
	
	private static IConcept getRoot(DirectedAcyclicGraph<IConcept, IIsA> dag){
		IConcept root = null;
		Iterator<IConcept> conceptIte = dag.vertexSet().iterator();
		while (root == null) {
			IConcept nextConcept = conceptIte.next();
			if (dag.outDegreeOf(root) == 0)
				root = nextConcept;
		}
		return root;
	}	
	
	private List<IConcept> getSortedParticulars(IConcept genus, Set<IComplementaryConcept> complementaryUpperBounds) {
		List<IConcept> sortedParticulars;
		Set<IConcept> unsortedParticulars = 
				new HashSet<>(Sets.intersection(conceptUSL.getLeaves(), conceptUSL.getDescendants(genus)));
		if (genus.isComplementary()) {
			List<Integer> particularIDs = new ArrayList<>();
			for (IContextObject obj : genus.getExtent())
				particularIDs.add(obj.iD());
			for (IConcept concept : conceptUSL.getLeaves()) {
				if (particularIDs.contains(concept.iD()))
					unsortedParticulars.add(concept);
			}
		}
		else {
			unsortedParticulars.addAll(conceptUSL.getLeaves());
			unsortedParticulars.retainAll(conceptUSL.getAncestors(genus));
			for (IComplementaryConcept compUB : complementaryUpperBounds)
				unsortedParticulars.removeAll(conceptUSL.getAncestors(compUB.getComplemented()));	
		}
		sortedParticulars = new ArrayList<>(unsortedParticulars);
		sortedParticulars.sort(iDComparator);
		return sortedParticulars;
	}
	
	private static Map<List<IConcept>, IConcept> mapClosedSubsetsOfParticulars2Supremum(
			InvertedUpperSemilattice<IConcept, IIsA> searchSpace, Set<IConcept> particulars) {
		Map<List<IConcept>, IConcept> closedSubset2Supremum = new HashMap<>();
		Set<IConcept> nonRootVertexSet = new HashSet<>(searchSpace.vertexSet());
		nonRootVertexSet.remove(searchSpace.getRoot());
		for (IConcept concept : nonRootVertexSet) {
			List<IConcept> closedSubset = new ArrayList<>();
			if (concept.type() == ConceptType.PARTICULAR)
				closedSubset.add(concept);
			else {
				closedSubset.addAll(Sets.intersection(searchSpace.getAncestors(concept), particulars));
				closedSubset.sort(iDComparator);
			}
			closedSubset2Supremum.put(closedSubset, concept);
		}
		return closedSubset2Supremum;
	}
	
	private static InvertedTree<IConcept, IIsA> asInvertedTree(DirectedAcyclicGraph<IConcept, IIsA> treeDAG, IConcept root) {
		List<IConcept> topoOrder = new ArrayList<>();
		new TopologicalOrderIterator<>(treeDAG).forEachRemaining(topoOrder::add);
		Set<IConcept> leaves = new HashSet<>();
		for (IConcept concept : topoOrder) {
			if (treeDAG.inDegreeOf(concept) == 0)
				leaves.add(concept);
		}
		return new InvertedTree<IConcept, IIsA>(treeDAG, root, leaves, topoOrder);
	}	
	
	//HERE
	public String visualize(DefaultDirectedGraph<IConcept, IIsA> graph, String fileName) {
		// convert in DOT format
		DOTExporter<IConcept, IIsA> exporter = new DOTExporter<>();
		exporter.setGraphAttributeProvider(() -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("rankdir", DefaultAttribute.createAttribute("BT"));
			return map;
		});
		exporter.setVertexAttributeProvider((v) -> {
			Map<String, Attribute> map = new LinkedHashMap<>();
			map.put("label", DefaultAttribute.createAttribute(v.toString()));
			return map;
		});
		Writer writer = new StringWriter();
		exporter.exportGraph(graph, writer);
		String stringDOT = writer.toString();
		// display graph
		try {
			MutableGraph dotGraph = new Parser().read(stringDOT);
			String filePath = LocalPaths.INSTANCE.getTargetFolderPath() + "\\" + fileName;
			Graphviz.fromGraph(dotGraph).render(Format.PNG).toFile(new File(filePath));
			return filePath + ".png";
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	//HERE

}
