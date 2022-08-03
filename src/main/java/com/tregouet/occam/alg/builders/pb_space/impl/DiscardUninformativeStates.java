package com.tregouet.occam.alg.builders.pb_space.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorer;
import com.tregouet.occam.alg.builders.pb_space.representations.RepresentationBuilder;
import com.tregouet.occam.alg.scorers.problem_states.ProblemStateScorer;
import com.tregouet.occam.alg.setters.weighs.categorization_transitions.ProblemTransitionWeigher;
import com.tregouet.occam.data.problem_space.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;
import com.tregouet.tree_finder.data.InvertedTree;

public class DiscardUninformativeStates implements ProblemSpaceExplorer {

	private IConceptLattice conceptLattice;
	private DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph;
	private Set<Integer> previouslyDeveloped = new HashSet<>();

	public DiscardUninformativeStates() {
	}

	@Override
	public void develop() {
		boolean newDevelopment = true;
		while (newDevelopment) {
			newDevelopment = false;
			Set<IRepresentation> representations = new HashSet<>(problemGraph.vertexSet());
			for (IRepresentation rep : representations) {
				boolean developed = develop(rep);
				if (developed)
					newDevelopment = true;
			}
		}
	}

	@Override
	public Boolean develop(int representationID) {
		IRepresentation current = getRepresentationWithID(representationID);
		if (current == null)
			return null;
		return develop(current);
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
	public ISimilarityMetrics getSimilarityMetrics() {
		return ProblemSpaceExplorer.similarityMetricsBuilder().apply(conceptLattice, problemGraph);
	}

	@Override
	public DiscardUninformativeStates initialize(
			Collection<IContextObject> context) {
		conceptLattice = ProblemSpaceExplorer.conceptLatticeBuilder().apply(context);
		InvertedTree<IConcept, IIsA> initialTree =
				new ArrayList<>(
						ProblemSpaceExplorer.getConceptTreeGrower().apply(conceptLattice, null).keySet()).get(0);
		IClassification classification =
				ProblemSpaceExplorer.classificationBuilder().apply(
						initialTree, conceptLattice.getParticularID2Particular());
		IRepresentation initialRepresentation = ProblemSpaceExplorer.representationBuilder().apply(classification);
		problemGraph = new DirectedAcyclicGraph<>(null, null, true);
		problemGraph.addVertex(initialRepresentation);
		weighThenScoreThenComply(problemGraph);
		return this;
	}

	@Override
	public Boolean restrictTo(Set<Integer> representationIDs) {
		Set<IRepresentation> restriction = new HashSet<>();
		for (Integer iD : representationIDs) {
			IRepresentation rep = getRepresentationWithID(iD);
			if (rep != null)
				restriction.add(rep);
			else return null;
		}
		if (restriction.equals(problemGraph.vertexSet()))
			return false;
		problemGraph = ProblemSpaceExplorer.problemSpaceGraphRestrictor().apply(problemGraph, restriction);
		weighThenScoreThenComply(problemGraph);
		previouslyDeveloped.clear();
		return true;
	}

	private Boolean develop(IRepresentation representation) {
		if (!representation.isExpandable() || previouslyDeveloped.contains(representation.iD()))
			return false;
		previouslyDeveloped.add(representation.iD());
		Map<InvertedTree<IConcept, IIsA>, Boolean> grownTree2Restricted =
				ProblemSpaceExplorer.getConceptTreeGrower().apply(
						conceptLattice, representation.getClassification().asGraph());
		RepresentationBuilder repBldr = ProblemSpaceExplorer.representationBuilder();
		Set<IRepresentation> newRepresentations = new HashSet<>();
		for (Entry<InvertedTree<IConcept, IIsA>, Boolean> tree2Restr : grownTree2Restricted.entrySet()) {
			InvertedTree<IConcept, IIsA> grownTree = tree2Restr.getKey();
			boolean restricted = tree2Restr.getValue();
			IClassification classification = ProblemSpaceExplorer.classificationBuilder().apply(grownTree,
					conceptLattice.getParticularID2Particular());
			if (restricted) //then grown tree has size 2 leaves which have already been developed in another tree
				classification.restrictFurtherExpansion();
			IRepresentation newRep = repBldr.apply(classification);
			newRepresentations.add(newRep);
		}
		ProblemSpaceExplorer.problemSpaceGraphExpander().apply(problemGraph, newRepresentations);
		weighThenScoreThenComply(problemGraph);
		return true;
	}

	private Boolean develop(List<IRepresentation> representations) {
		int initialNbOfStates = problemGraph.vertexSet().size();
		for (IRepresentation representation : representations) {
			develop(representation);
		}
		int finalNbOfStates = problemGraph.vertexSet().size();
		return initialNbOfStates < finalNbOfStates;
	}

	private IRepresentation getRepresentationWithID(int iD) {
		for (IRepresentation representation : problemGraph.vertexSet()) {
			if (representation.iD() == iD)
				return representation;
		}
		return null;
	}

	private static void removeUninformative(
			DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph) {
		Set<IRepresentation> representations = new HashSet<>(problemGraph.vertexSet());
		for (IRepresentation representation : representations) {
			if (representation.score() == 0.0)
				problemGraph.removeVertex(representation);
		}
	}

	private static void weighThenScoreThenComply(
			DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph) {
		ProblemTransitionWeigher weigher = ProblemSpaceExplorer.getProblemTransitionWeigher().setContext(problemGraph);
		for (AProblemStateTransition transition : problemGraph.edgeSet())
			weigher.accept(transition);
		ProblemStateScorer scorer = ProblemSpaceExplorer.getProblemStateScorer().setUp(problemGraph);
		for (IRepresentation problemState : problemGraph)
			problemState.setScore(scorer.score(problemState));
		removeUninformative(problemGraph);
	}

	@Override
	public Boolean develop(Set<Integer> representationIDs) {
		List<IRepresentation> representations = new ArrayList<>();
		for (Integer repID : representationIDs) {
			IRepresentation current = getRepresentationWithID(repID);
			if (current != null)
				representations.add(current);
		}
		return develop(representations);
	}

}
