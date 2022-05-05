package com.tregouet.occam.alg.builders.representations.concept_trees.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.builders.representations.concept_trees.ConceptTreeGrower;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.concepts.impl.Everything;
import com.tregouet.occam.data.representations.concepts.impl.IsA;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.data.InvertedUpperSemilattice;
import com.tregouet.tree_finder.utils.Functions;

public class PartitionUnsortedUniversals implements ConceptTreeGrower {
	
	public static final PartitionUnsortedUniversals INSTANCE = new PartitionUnsortedUniversals();
	
	private PartitionUnsortedUniversals() {
	}

	@Override
	public Set<InvertedTree<IConcept, IIsA>> apply(IConceptLattice conceptLattice, InvertedTree<IConcept, IIsA> currentTree) {
		if (currentTree == null) {
			Set<InvertedTree<IConcept, IIsA>> initial = new HashSet<>();
			initial.add(initialize(conceptLattice));
			return initial;
		}
		
	}
	
	private static InvertedTree<IConcept, IIsA> initialize(IConceptLattice conceptLattice) {
		DirectedAcyclicGraph<IConcept, IIsA> initialGraph = new DirectedAcyclicGraph<>(null, null, false);
		InvertedUpperSemilattice<IConcept, IIsA> conceptUSL = conceptLattice.getOntologicalUpperSemilattice();
		IConcept ontologicalCommitment = conceptLattice.getOntologicalCommitment();
		IConcept truism = conceptLattice.getTruism();
		IIsA initialEdge = conceptUSL.getEdge(truism, ontologicalCommitment);
		initialGraph.addVertex(truism);
		initialGraph.addVertex(ontologicalCommitment);
		initialGraph.addEdge(truism, ontologicalCommitment, initialEdge);
		Set<IConcept> leaves = new HashSet<>(Arrays.asList(new IConcept[] {truism}));
		List<IConcept> topoOrderedSet = new ArrayList<>(Arrays.asList(new IConcept[] {truism, ontologicalCommitment}));
		InvertedTree<IConcept, IIsA> initialTree = 
				new InvertedTree<IConcept, IIsA>(initialGraph, ontologicalCommitment, leaves, topoOrderedSet); 
		return initialTree;
	}
	
	private static  Set<Set<IConcept>> sort(IConceptLattice lattice, IConcept genus) {
		Set<Set<IConcept>> partitions = new HashSet<>();
		DirectedAcyclicGraph<IConcept, IIsA> searchSpace;
	}
	
	private static DirectedAcyclicGraph<IConcept, IIsA> buildSearchSpace(IConceptLattice lattice, IConcept genus) {
		DirectedAcyclicGraph<IConcept, IIsA> searchSpace = new DirectedAcyclicGraph<>(null, null, false);
		InvertedUpperSemilattice<IConcept, IIsA> conceptUSL = lattice.getOntologicalUpperSemilattice();
		if (genus.isComplementary()) {
			//build set of particulars
			Set<Integer> extentIDs = new HashSet<>();
			for (IContextObject obj : genus.getExtent())
				extentIDs.add(obj.iD());
			Set<IConcept> particulars = new HashSet<>();
			for (IConcept particular : lattice.getParticulars()) {
				if (extentIDs.contains(particular.iD()))
					particulars.add(particular);
			}
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
					searchSpace.addEdge(conceptUSL.getEdgeSource(isA), target, isA);
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
			//replace search space root by complementary genus
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

}
