package com.tregouet.occam.alg.builders.pb_space.concepts_trees.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.Graphs;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.builders.pb_space.concepts_trees.ConceptTreeGrower;
import com.tregouet.occam.data.representations.classifications.concepts.ConceptType;
import com.tregouet.occam.data.representations.classifications.concepts.IComplementaryConcept;
import com.tregouet.occam.data.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.representations.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.classifications.concepts.IIsA;
import com.tregouet.occam.data.representations.classifications.concepts.impl.ComplementaryConcept;
import com.tregouet.occam.data.representations.classifications.concepts.impl.IsA;
import com.tregouet.partitioner.IPartitioner;
import com.tregouet.partitioner.impl.ConstrainedPartitioner;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.data.InvertedUpperSemilattice;
import com.tregouet.tree_finder.utils.Functions;

public class IfLeafIsUniversalThenSort implements ConceptTreeGrower {

	private static final Comparator<IConcept> iDComparator = (x, y) -> x.iD() - y.iD();

	public IfLeafIsUniversalThenSort() {
	}

	@Override
	public Map<InvertedTree<IConcept, IIsA>, Boolean> apply(IConceptLattice conceptLattice, InvertedTree<IConcept, IIsA> currentTree) {
		if (currentTree == null) {
			Map<InvertedTree<IConcept, IIsA>, Boolean> initial = new HashMap<>();
			initial.put(initialTree(conceptLattice), true);
			return initial;
		}
		Map<InvertedTree<IConcept, IIsA>, Boolean> expandedTrees2Restricted = new HashMap<>();
		Set<IConcept> leaves = currentTree.getLeaves();
		List<IConcept> unsorted = new ArrayList<>();
		for (IConcept leaf : leaves) {
			if (leaf.type() != ConceptType.PARTICULAR)
				unsorted.add(leaf);
		}
		for (IConcept genus : unsorted) {
			InvertedUpperSemilattice<IConcept, IIsA> searchSpace =
					buildSearchUSL(genus, currentTree, conceptLattice.getOntologicalUpperSemilattice());
			Set<IConcept> particulars = getLeaves(searchSpace);
			List<IConcept> sortedParticulars = new ArrayList<>(particulars);
			sortedParticulars.sort(iDComparator);
			Map<List<IConcept>, IConcept> closedSubsetsOfParticulars2Supremum =
					mapClosedSubsetsOfParticulars2Supremum(searchSpace, particulars);
			//classify genus extent
			List<List<IConcept>> genusSortings =
					classify(sortedParticulars, closedSubsetsOfParticulars2Supremum);
			for (List<IConcept> speciesSet : genusSortings) {
				DirectedAcyclicGraph<IConcept, IIsA> treeDAG = new DirectedAcyclicGraph<>(null, IsA::new, false);
				Graphs.addAllVertices(treeDAG, currentTree.vertexSet());
				Graphs.addAllEdges(treeDAG, currentTree, currentTree.edgeSet());
				Graphs.addAllVertices(treeDAG, speciesSet);
				for (IConcept species : speciesSet)
					treeDAG.addEdge(species, genus);
				addExpandedTree(treeDAG, expandedTrees2Restricted, searchSpace, currentTree.getRoot());
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
				addExpandedTree(treeDAG, expandedTrees2Restricted, searchSpace, currentTree.getRoot());
			}
		}
		return expandedTrees2Restricted;
	}

	private void addExpandedTree(DirectedAcyclicGraph<IConcept, IIsA> treeDAG,
			Map<InvertedTree<IConcept, IIsA>, Boolean> expandedTrees2Expandable,
			InvertedUpperSemilattice<IConcept, IIsA> searchSpace, IConcept root) {
		DirectedAcyclicGraph<IConcept, IIsA> developedTreeDAG = developTreesWithSize2Leaves(treeDAG, searchSpace);
		if (developedTreeDAG != null) {
			expandedTrees2Expandable.put(asInvertedTree(developedTreeDAG, root), false);
			expandedTrees2Expandable.put(asInvertedTree(treeDAG, root), true);
		}
		else expandedTrees2Expandable.put(asInvertedTree(treeDAG, root), false);
	}

	private DirectedAcyclicGraph<IConcept, IIsA> developTreesWithSize2Leaves(DirectedAcyclicGraph<IConcept, IIsA> treeDAG,
			InvertedUpperSemilattice<IConcept, IIsA> searchSpace) {
		DirectedAcyclicGraph<IConcept, IIsA> additionalTree = null;
		Map<IConcept, Set<Integer>> trivialLeafID2ExtentIDs = getTrivialLeafID2ExtentIDs(treeDAG, searchSpace);
		if (!trivialLeafID2ExtentIDs.isEmpty()) {
			additionalTree = new DirectedAcyclicGraph<>(null, IsA::new, false);
			//clone treeDAG
			Graphs.addAllVertices(additionalTree, treeDAG.vertexSet());
			Graphs.addAllEdges(additionalTree, treeDAG, treeDAG.edgeSet());
			//develop trivial leaves
			for (Entry<IConcept, Set<Integer>> leaf2Extent : trivialLeafID2ExtentIDs.entrySet()) {
				IConcept trivialLeaf = leaf2Extent.getKey();
				for (Integer extentID : leaf2Extent.getValue()) {
					IConcept particular = getParticularWithID(extentID, searchSpace);
					additionalTree.addVertex(particular);
					additionalTree.addEdge(particular, trivialLeaf);
				}
			}
		}
		return additionalTree;
	}

	protected static Set<IConcept> getLeaves(DirectedAcyclicGraph<IConcept, IIsA> dag){
		Set<IConcept> leaves = new HashSet<>();
		for (IConcept concept : dag) {
			if (dag.inDegreeOf(concept) == 0)
				leaves.add(concept);
		}
		return leaves;
	}

	private static InvertedTree<IConcept, IIsA> asInvertedTree(DirectedAcyclicGraph<IConcept, IIsA> treeDAG, IConcept root) {
		List<IConcept> topoOrder = new ArrayList<>();
		new TopologicalOrderIterator<>(treeDAG).forEachRemaining(topoOrder::add);
		Set<IConcept> leaves = new HashSet<>();
		for (IConcept concept : topoOrder) {
			if (treeDAG.inDegreeOf(concept) == 0)
				leaves.add(concept);
		}
		return new InvertedTree<>(treeDAG, root, leaves, topoOrder);
	}

	private static InvertedUpperSemilattice<IConcept, IIsA> asInvertedUpperSemilatticeWithSpecifiedRoot(
			DirectedAcyclicGraph<IConcept, IIsA> lowerSet, IConcept root) {
		//if already has a root, then replaced by specified root
		Set<IConcept> maxima = Functions.maxima(lowerSet);
		if (maxima.size() == 1) {
			IConcept maximum = new ArrayList<>(maxima).get(0);
			Set<IConcept> predecessors = new HashSet<>();
			for (IIsA isA : lowerSet.incomingEdgesOf(maximum))
				predecessors.add(lowerSet.getEdgeSource(isA));
			lowerSet.removeVertex(maximum);
			lowerSet.addVertex(root);
			for (IConcept predecessor : predecessors)
				lowerSet.addEdge(predecessor, root);
		}
		//otherwise, add specify root
		else {
			lowerSet.addVertex(root);
			for (IConcept predecessor : maxima) {
				lowerSet.addEdge(predecessor, root);
			}
		}
		List<IConcept> topoOrder = new ArrayList<>();
		Set<IConcept> leaves = new HashSet<>();
		Iterator<IConcept> topoOrderIte = new TopologicalOrderIterator<>(lowerSet);
		while (topoOrderIte.hasNext()) {
			IConcept nextConcept = topoOrderIte.next();
			topoOrder.add(nextConcept);
			if (lowerSet.inDegreeOf(nextConcept) == 0)
				leaves.add(nextConcept);
		}
		return new InvertedUpperSemilattice<>(lowerSet, root, leaves, topoOrder);
	}

	private static InvertedUpperSemilattice<IConcept, IIsA> buildSearchUSL(IConcept genus,
			InvertedTree<IConcept, IIsA> currentTree, InvertedUpperSemilattice<IConcept, IIsA> conceptUSL) {
		Set<IConcept> searchSpaceConcepts = new HashSet<>(conceptUSL.vertexSet());
		IConcept currentConcept = genus;
		IConcept root = currentTree.getRoot();
		boolean uslConceptHasBeenVisited = false;
		while (!currentConcept.equals(root)) {
			if (currentConcept.isComplementary()) {
				IComplementaryConcept currentCompConcept = (IComplementaryConcept) currentConcept;
				IConcept wrappedComplementing = currentCompConcept.getWrappedComplementing();
				if (!uslConceptHasBeenVisited && wrappedComplementing != null) {
					searchSpaceConcepts.retainAll(conceptUSL.getAncestors(wrappedComplementing));
					uslConceptHasBeenVisited = true;
				}
				searchSpaceConcepts.removeAll(
						Functions.lowerSet(conceptUSL, currentCompConcept.getComplemented()));
			}
			else if (!uslConceptHasBeenVisited) {
				searchSpaceConcepts.retainAll(conceptUSL.getAncestors(currentConcept));
				uslConceptHasBeenVisited = true;
			}
			currentConcept = currentTree.getEdgeTarget(new ArrayList<>(currentTree.outgoingEdgesOf(currentConcept)).get(0));
		}
		DirectedAcyclicGraph<IConcept, IIsA> searchSpace = new DirectedAcyclicGraph<>(null, IsA::new, false);
		Graphs.addAllVertices(searchSpace, searchSpaceConcepts);
		for (IIsA isA : conceptUSL.edgeSet()) {
			IConcept source = conceptUSL.getEdgeSource(isA);
			IConcept target = conceptUSL.getEdgeTarget(isA);
			if (searchSpaceConcepts.contains(source)
					&& searchSpaceConcepts.contains(target))
				searchSpace.addEdge(source, target, isA);
		}
		Set<IConcept> particularsUpperSets = new HashSet<>();
		for (IConcept ssConcept : searchSpace) {
			if (ssConcept.type() == ConceptType.PARTICULAR)
				particularsUpperSets.addAll(Functions.upperSet(searchSpace, ssConcept));
		}
		for (IConcept ssCOncept : searchSpaceConcepts) {
			if (!particularsUpperSets.contains(ssCOncept))
				searchSpace.removeVertex(ssCOncept);
		}
		//make the search space atomistic
		makeAtomistic(searchSpace);
		InvertedUpperSemilattice<IConcept, IIsA> searchUSL = asInvertedUpperSemilatticeWithSpecifiedRoot(searchSpace, genus);
		return searchUSL;
	}

	private static List<List<IConcept>> classify(List<IConcept> sortedParticulars,
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
					Set<Integer> compExtentIDs = new HashSet<>();
					for (IConcept particular : nonCParticulars)
						compExtentIDs.addAll(particular.getMaxExtentIDs());
					complementary = new ComplementaryConcept(c, genus, compExtentIDs);
				}
				else {
					complementary = new ComplementaryConcept(c, nonCSupremum);
				}
				dichotomies.add(new Pair<>(c, complementary));
			}
		}
		return dichotomies;
	}

	private static IConcept getParticularWithID(int iD, InvertedUpperSemilattice<IConcept, IIsA> searchSpace) {
		for (IConcept concept : searchSpace.getLeaves()) {
			if (concept.iD() == iD)
				return concept;
		}
		return null;
	}

	private static Map<IConcept, Set<Integer>> getTrivialLeafID2ExtentIDs(DirectedAcyclicGraph<IConcept, IIsA> treeDAG,
			InvertedUpperSemilattice<IConcept, IIsA> searchSpace) {
		Map<IConcept, Set<Integer>> trivialLeafID2ExtentIDs = new HashMap<>();
		Set<Integer> searchSpaceParticularIDs = new HashSet<>();
		for (IConcept searchSpaceLeaf : getLeaves(searchSpace))
			searchSpaceParticularIDs.add(searchSpaceLeaf.iD());
		for(IConcept leaf : getLeaves(treeDAG)) {
			if (leaf.type() != ConceptType.PARTICULAR) {
				Set<Integer> extentIDs = Sets.intersection(leaf.getMaxExtentIDs(), searchSpaceParticularIDs);
				if (extentIDs.size() == 2)
					trivialLeafID2ExtentIDs.put(leaf, extentIDs);
			}
		}
		return trivialLeafID2ExtentIDs;
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
				new InvertedTree<>(initialGraph, ontologicalCommitment, leaves, topoOrderedSet);
		return initialTree;
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

}
