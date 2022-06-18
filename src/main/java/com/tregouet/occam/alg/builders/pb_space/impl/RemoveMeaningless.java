package com.tregouet.occam.alg.builders.pb_space.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorer;
import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorer;
import com.tregouet.occam.alg.setters.weighs.categorization_transitions.ProblemTransitionWeigher;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;
import com.tregouet.tree_finder.data.InvertedTree;

public class RemoveMeaningless implements ProblemSpaceExplorer {

	protected IConceptLattice conceptLattice;
	protected Set<Integer> extentIDs;
	protected Set<IContextualizedProduction> productions;
	protected DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph;

	public RemoveMeaningless() {
	}

	@Override
	public Boolean apply(Integer representationID) {
		/*
		IRepresentation current = getRepresentationWithID(representationID);
		if (current == null)
			return null;
		if (current.isFullyDeveloped())
			return false;
		DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> newProblemGraph =
				new DirectedAcyclicGraph<>(null, null, false);
		Graphs.addAllVertices(newProblemGraph, problemGraph.vertexSet());
		Set<InvertedTree<IConcept, IIsA>> grownTrees =
				ProblemSpaceExplorer.getConceptTreeGrower().apply(conceptLattice, current.getClassification().asGraph());
		Set<IRepresentation> newRepresentations = new HashSet<>();
		for (InvertedTree<IConcept, IIsA> grownTree : grownTrees) {
			IClassification classification = ProblemSpaceExplorer.classificationBuilder().apply(grownTree, extentIDs);
			IRepresentation developed = ProblemSpaceExplorer.getRepresentationBuilder().apply(classification);
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
		reduceThenWeightThenScoreThenComply(newProblemGraph);
		if (!(newProblemGraph.vertexSet().size() > problemGraph.vertexSet().size()))
			return false;
		this.problemGraph = newProblemGraph;
		//overloadable ; do nothing in this impl
		expandTransitoryLeaves(newRepresentations);
		*/
		return true;
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

	@Override
	public DirectedAcyclicGraph<IConcept, IIsA> getLatticeOfConcepts() {
		return conceptLattice.getLatticeOfConcepts();
	}

	@Override
	public DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> getProblemSpaceGraph() {
		return problemGraph;
	}

	@Override
	public RemoveMeaningless initialize(
			Collection<IContextObject> context) {
		conceptLattice = ProblemSpaceExplorer.conceptLatticeBuilder().apply(context);
		extentIDs = new HashSet<>();
		for (IContextObject object : conceptLattice.getContextObjects())
			extentIDs.add(object.iD());
		InvertedTree<IConcept, IIsA> initialTree =
				new ArrayList<>(
						ProblemSpaceExplorer.getConceptTreeGrower().apply(conceptLattice, null)).get(0);
		IClassification classification = ProblemSpaceExplorer.classificationBuilder().apply(initialTree, extentIDs);
		IRepresentation initialRepresentation = ProblemSpaceExplorer.getRepresentationBuilder().apply(classification);
		problemGraph = new DirectedAcyclicGraph<>(null, null, true);
		problemGraph.addVertex(initialRepresentation);
		reduceThenWeightThenScoreThenComply(problemGraph);
		return this;
	}

	protected void complyToAdditionalConstraint(
			DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> graph) {
		//do nothing
	}

	protected void expandTransitoryLeaves(Set<IRepresentation> newRepresentations) {
		//do nothing
	}

	protected IRepresentation getRepresentationWithID(int iD) {
		for (IRepresentation representation : problemGraph.vertexSet()) {
			if (representation.iD() == iD)
				return representation;
		}
		return null;
	}

	protected void reduceThenWeightThenScoreThenComply(
			DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph) {
		TransitiveReduction.INSTANCE.reduce(problemGraph);
		ProblemTransitionWeigher weigher = ProblemSpaceExplorer.getProblemTransitionWeigher().setContext(problemGraph);
		for (AProblemStateTransition transition : problemGraph.edgeSet())
			weigher.accept(transition);
		ProblemStateScorer scorer = ProblemSpaceExplorer.getProblemStateScorer().setUp(problemGraph);
		for (IRepresentation problemState : problemGraph)
			problemState.setScore(scorer.apply(problemState));
		complyToAdditionalConstraint(problemGraph);
	}



}
