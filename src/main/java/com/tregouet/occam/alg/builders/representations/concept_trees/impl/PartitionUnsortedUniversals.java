package com.tregouet.occam.alg.builders.representations.concept_trees.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.jgrapht.Graphs;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.builders.representations.concept_trees.ConceptTreeGrower;
import com.tregouet.occam.data.representations.concepts.ConceptType;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.concepts.impl.Everything;
import com.tregouet.occam.data.representations.concepts.impl.IsA;
import com.tregouet.partitioner.IPartitioner;
import com.tregouet.partitioner.impl.ConstrainedPartitioner;
import com.tregouet.partitioner.impl.Partitioner;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.data.InvertedUpperSemilattice;
import com.tregouet.tree_finder.utils.Functions;

public class PartitionUnsortedUniversals implements ConceptTreeGrower {
	
	private static final Comparator<IConcept> iDComparator = (x, y) -> x.iD() - y.iD();
	private IConceptLattice conceptLattice;
	private InvertedUpperSemilattice<IConcept, IIsA> conceptUSL;
	private List<IConcept> topoOrder;
	private List<Set<IConcept>> lowerSets;
	
	public PartitionUnsortedUniversals() {
	}

	@Override
	public Set<InvertedTree<IConcept, IIsA>> apply(IConceptLattice conceptLattice, InvertedTree<IConcept, IIsA> currentTree) {
		Set<InvertedTree<IConcept, IIsA>> expandedTrees = new HashSet<>();
		if (currentTree == null) {
			Set<InvertedTree<IConcept, IIsA>> initial = new HashSet<>();
			initial.add(initialTree(conceptLattice));
			return initial;
		}
		initialize(conceptLattice);
		Set<IConcept> leaves = currentTree.getLeaves();
		List<IConcept> unsorted = new ArrayList<>();
		for (IConcept leaf : leaves) {
			if (leaf.type() != ConceptType.PARTICULAR)
				unsorted.add(leaf);
		}
		for (IConcept genus : unsorted) {
			List<List<List<IConcept>>> cleanPartitions = new ArrayList<>();
			List<Pair<List<IConcept>, List<IConcept>>> dichotomies = new ArrayList<>();
			populateLists(genus, cleanPartitions, dichotomies);
		}
	}
	
	private void initialize(IConceptLattice conceptLattice) {
		this.conceptLattice = conceptLattice;
		conceptUSL = conceptLattice.getOntologicalUpperSemilattice();
		topoOrder = conceptUSL.getTopologicalOrder();
		lowerSets = new ArrayList<>();
		for (IConcept concept : topoOrder)
			lowerSets.add(Functions.lowerSet(conceptUSL, concept));
	}
	
	private static Set<InvertedTree<IConcept, IIsA>> partition(InvertedTree<IConcept, IIsA> currentTree, IConcept genus, 
			List<List<List<IConcept>>> cleanPartitions) {
		InvertedTree<IConcept, IIsA> bloomingTree = shallowCopyOf(currentTree);
		for (List<List<IConcept>> partition : cleanPartitions) {
			List<IConcept> species = new ArrayList<>();
			for (List<IConcept> subset : partition)
		}
	}
	
	private static InvertedTree<IConcept, IIsA> initialTree(IConceptLattice conceptLattice) {
		DirectedAcyclicGraph<IConcept, IIsA> initialGraph = new DirectedAcyclicGraph<>(null, null, false);
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
	
	private void populateLists(IConcept genus, List<List<List<IConcept>>> cleanPartitions, 
			List<Pair<List<IConcept>, List<IConcept>>> dichotomies) {
		//build search space
		List<IConcept> sortedParticulars = getSortedParticulars(genus);
		Set<IConcept> particulars = new HashSet<>(sortedParticulars);
		DirectedAcyclicGraph<IConcept, IIsA> searchSpace = buildSearchSpace(genus, particulars);
		Set<List<IConcept>> closedSubsetsOfParticulars = getClosedSubsetsOfParticulars(searchSpace, particulars);
		//build clean partitions
		IPartitioner<IConcept> cleanPartitioner = new ConstrainedPartitioner<>(sortedParticulars, closedSubsetsOfParticulars, null);
		cleanPartitions.addAll(cleanPartitioner.getAllPartitions());
		//build unidimensional dichotomies
		List<List<List<IConcept>>> size2Partitions;
		IPartitioner<IConcept> size2Partitioner = new ConstrainedPartitioner<>(sortedParticulars, null, 2);
		size2Partitions = size2Partitioner.getAllPartitions();
		for (List<List<IConcept>> size2Partition : size2Partitions) {
			List<IConcept> extentOfC = null;
			List<IConcept> extentOfNonC = null;
			List<IConcept> firstPartition = size2Partition.get(0);
			List<IConcept> secondPartition = size2Partition.get(1);
			if (closedSubsetsOfParticulars.contains(firstPartition))
				extentOfC = firstPartition;
			else extentOfNonC = firstPartition;
			if (closedSubsetsOfParticulars.contains(secondPartition)) {
				if (extentOfC == null);
				extentOfC = secondPartition;
			}
			else if (extentOfNonC == null)
				extentOfNonC = secondPartition;
			if (extentOfC != null && extentOfNonC != null)
				dichotomies.add(new Pair<List<IConcept>, List<IConcept>>(extentOfC, extentOfNonC));
		}
	}
	
	private DirectedAcyclicGraph<IConcept, IIsA> buildSearchSpace(IConcept genus, Set<IConcept> particulars) {
		DirectedAcyclicGraph<IConcept, IIsA> searchSpace = new DirectedAcyclicGraph<>(null, null, false);
		if (genus.isComplementary()) {
			//build search space without genus
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
			//make search space atomistic
			List<IConcept> topoOrder = new ArrayList<>();
			List<Set<IConcept>> lowerSets = new ArrayList<>();
			List<Set<IConcept>> lowerBoundParticulars = new ArrayList<>();
			new TopologicalOrderIterator<>(searchSpace).forEachRemaining(topoOrder::add);
			for (int i = 0 ; i < topoOrder.size() ; i++) {
				IConcept iConcept = topoOrder.get(i);
				Set<IConcept> iLowerSet = Functions.lowerSet(searchSpace, iConcept);
				Set<IConcept> iParticulars = new HashSet<>(Sets.intersection(iLowerSet, particulars));
				if (lowerBoundParticulars.contains(iParticulars)) {
					topoOrder.remove(i);
					Functions.removeVertexAndPreserveConnectivity(searchSpace, iConcept);
					i--;
				}
				else {
					lowerSets.add(iLowerSet);
					lowerBoundParticulars.add(iParticulars);
				}
			}
			//replace search space root by genus HERE POUR QUOI FAIRE ? 
			List<IConcept> supremumPredecessors = new ArrayList<>();
			for (IIsA incomingEdge : searchSpace.incomingEdgesOf(supremumOfParticulars))
				supremumPredecessors.add(searchSpace.getEdgeSource(incomingEdge));
			searchSpace.removeVertex(supremumOfParticulars);
			searchSpace.addVertex(genus);
			for (IConcept predecessor : supremumPredecessors)
				searchSpace.addEdge(predecessor, genus, new IsA());
			
		}
		else {
			Set<IConcept> genusLowerSet = Functions.lowerSet(conceptUSL, genus);
			Graphs.addAllVertices(searchSpace, genusLowerSet);
			for (IIsA isA : conceptUSL.edgeSet()) {
				IConcept target = conceptUSL.getEdgeTarget(isA); 
				if (genusLowerSet.contains(target)) //then it contains edge source
					searchSpace.addEdge(conceptUSL.getEdgeSource(isA), target, isA);
			}
		}
		return searchSpace;
	}
	
	private List<IConcept> getSortedParticulars(IConcept genus) {
		Set<Integer> extentIDs = new HashSet<>();
		for (IContextObject obj : genus.getExtent())
			extentIDs.add(obj.iD());
		List<IConcept> particulars = new ArrayList<>();
		for (IConcept particular : conceptLattice.getParticulars()) {
			if (extentIDs.contains(particular.iD()))
				particulars.add(particular);
		}
		particulars.sort(iDComparator);
		return particulars;
	}
	
	//HERE transformer en Map
	private static Set<List<IConcept>> getClosedSubsetsOfParticulars(DirectedAcyclicGraph<IConcept, IIsA> searchSpace, 
			Set<IConcept> particulars) {
		Set<List<IConcept>> closedSubsets = new HashSet<>();
		for (IConcept concept : searchSpace.vertexSet()) {
			List<IConcept> closedSubset = new ArrayList<>();
			if (concept.type() == ConceptType.PARTICULAR)
				closedSubset.add(concept);
			else {
				closedSubset.addAll(Sets.intersection(searchSpace.getAncestors(concept), particulars));
				closedSubset.sort(iDComparator);
			}
			closedSubsets.add(closedSubset);
		}
		return closedSubsets;
	}
	
	private static InvertedTree<IConcept, IIsA> shallowCopyOf(InvertedTree<IConcept, IIsA> tree) {
		DirectedAcyclicGraph<IConcept, IIsA> copy = new DirectedAcyclicGraph<>(null, null, false);
		Graphs.addAllVertices(copy, tree.vertexSet());
		Graphs.addAllEdges(copy, tree, tree.edgeSet());
		return new InvertedTree<IConcept, IIsA>(copy, tree.getRoot(), tree.getLeaves(), tree.getTopologicalOrder());
	}

}
