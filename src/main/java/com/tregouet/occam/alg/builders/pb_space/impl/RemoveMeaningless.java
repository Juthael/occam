package com.tregouet.occam.alg.builders.pb_space.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.jgrapht.Graphs;
import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorer;
import com.tregouet.occam.alg.builders.pb_space.ranker.ProblemTransitionRanker;
import com.tregouet.occam.alg.builders.pb_space.representations.RepresentationBuilder;
import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorer;
import com.tregouet.occam.alg.setters.weighs.categorization_transitions.ProblemTransitionWeigher;
import com.tregouet.occam.data.problem_spaces.AProblemStateTransition;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.topo_order.TopoOrderOverReps;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;
import com.tregouet.tree_finder.data.InvertedTree;

public class RemoveMeaningless implements ProblemSpaceExplorer {
	
	protected IConceptLattice conceptLattice;
	protected Set<IContextualizedProduction> productions;
	protected DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph;
	
	public RemoveMeaningless() {
	}

	@Override
	public Boolean apply(Integer representationID) {
		IRepresentation current = getRepresentationWithID(representationID);
		if (current == null)
			return null;
		if (current.isFullyDeveloped())
			return false;
		DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> newProblemGraph = 
				new DirectedAcyclicGraph<>(null, null, false);
		Graphs.addAllVertices(newProblemGraph, problemGraph.vertexSet());
		Set<InvertedTree<IConcept, IIsA>> grownTrees = 
				ProblemSpaceExplorer.getConceptTreeGrower().apply(conceptLattice, current.getTreeOfConcepts());
		RepresentationBuilder repBldr = ProblemSpaceExplorer.getRepresentationBuilder().setUp(productions);
		Set<IRepresentation> newRepresentations = new HashSet<>();
		for (InvertedTree<IConcept, IIsA> grownTree : grownTrees) {
			IRepresentation developed = repBldr.apply(grownTree);
			newRepresentations.add(developed);
		}
		Graphs.addAllVertices(newProblemGraph, newRepresentations);
		SortedSet<IRepresentation> topoOrderedRep = new TreeSet<>(TopoOrderOverReps.INSTANCE);
		topoOrderedRep.addAll(newProblemGraph.vertexSet());
		Set<AProblemStateTransition> transitions = 
				ProblemSpaceExplorer.getProblemTransitionBuilder().apply(new ArrayList<>(topoOrderedRep));
		for (AProblemStateTransition transition : transitions) {
			newProblemGraph.addEdge(transition.getSource(), transition.getTarget(), transition);
		}
		reduceThenRankThenWeightThenScoreThenFilter(newProblemGraph);
		if (!(newProblemGraph.vertexSet().size() > problemGraph.vertexSet().size()))
			return false;
		this.problemGraph = newProblemGraph;
		//overloadable ; do nothing in this impl
		expandForbiddenLeaves(newRepresentations);
		return true;
	}

	@Override
	public RemoveMeaningless initialize(
			Collection<IContextObject> context) {
		conceptLattice = ProblemSpaceExplorer.getConceptLatticeBuilder().apply(context);
		productions = ProblemSpaceExplorer.getProductionBuilder().apply(conceptLattice);
		InvertedTree<IConcept, IIsA> initialTree = 
				new ArrayList<InvertedTree<IConcept, IIsA>>(
						ProblemSpaceExplorer.getConceptTreeGrower().apply(conceptLattice, null)).get(0);
		RepresentationBuilder repBldr = ProblemSpaceExplorer.getRepresentationBuilder().setUp(productions);
		IRepresentation initialRepresentation = repBldr.apply(initialTree);
		problemGraph = new DirectedAcyclicGraph<>(null, null, true);
		problemGraph.addVertex(initialRepresentation);
		reduceThenRankThenWeightThenScoreThenFilter(problemGraph);
		return this;
	}
	
	protected void reduceThenRankThenWeightThenScoreThenFilter(
			DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph) {
		TransitiveReduction.INSTANCE.reduce(problemGraph);
		ProblemTransitionRanker ranker = ProblemSpaceExplorer.getProblemTransitionRanker().setUp(problemGraph);
		ProblemTransitionWeigher weigher = ProblemSpaceExplorer.getProblemTransitionWeigher().setContext(problemGraph);
		for (AProblemStateTransition transition : problemGraph.edgeSet()) {
			ranker.accept(transition);
			weigher.accept(transition);
		}
		ProblemStateScorer scorer = ProblemSpaceExplorer.getProblemStateScorer().setUp(problemGraph);
		for (IRepresentation problemState : problemGraph)
			problemState.setScore(scorer.apply(problemState));
		filter(problemGraph);
	}
	
	protected IRepresentation getRepresentationWithID(int iD) {
		for (IRepresentation representation : problemGraph.vertexSet()) {
			if (representation.iD() == iD)
				return representation;
		}
		return null;
	}

	@Override
	public DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> getProblemSpaceGraph() {
		return problemGraph;
	}
	
	protected void filter(
			DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> graph) {
		//do nothing
	}

	@Override
	public Set<Integer> getIDsOfRepresentationsWithIncompleteSorting() {
		Set<Integer> iDs = new HashSet<>();
		for (IRepresentation representation : problemGraph.vertexSet()) {
			if (!representation.isFullyDeveloped())
				iDs.add(representation.iD());
		}
		return iDs;
	}
	
	protected void expandForbiddenLeaves(Set<IRepresentation> newRepresentations) {
		//do nothing
	}

}
