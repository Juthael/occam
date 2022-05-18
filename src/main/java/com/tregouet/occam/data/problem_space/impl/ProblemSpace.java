package com.tregouet.occam.data.problem_space.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorer;
import com.tregouet.occam.data.problem_space.IProblemSpace;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.AbstractDifferentiae;
import com.tregouet.occam.data.problem_space.states.evaluation.facts.IFact;
import com.tregouet.occam.data.problem_space.states.transitions.AConceptTransitionSet;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;
import com.tregouet.tree_finder.data.Tree;

public class ProblemSpace implements IProblemSpace {

	private List<IContextObject> context;
	private final ProblemSpaceExplorer problemSpaceExplorer;
	private IRepresentation activeState = null;

	public ProblemSpace(Set<IContextObject> context) {
		this.context = new ArrayList<>(context);
		this.context.sort((x, y) -> Integer.compare(x.iD(), y.iD()));
		problemSpaceExplorer = IProblemSpace.problemSpaceExplorer().initialize(this.context);
	}

	@Override
	public DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> getProblemSpaceGraph() {
		return problemSpaceExplorer.getProblemSpaceGraph();
	}

	@Override
	public Tree<Integer, AbstractDifferentiae> getActiveRepresentationDescriptionGraph() {
		if (activeState == null)
			return null;
		return activeState.getDescription().asGraph();
	}

	@Override
	public double[][] getAsymmetricalSimilarityMatrixOfActiveRepresentation() {
		if (activeState == null)
			return null;
		return activeState.getDescription().getSimilarityMetrics().getAsymmetricalSimilarityMatrix();
	}

	@Override
	public double[] getTypicalityVectorOfActiveRepresentation() {
		if (activeState == null)
			return null;
		return activeState.getDescription().getSimilarityMetrics().getTypicalityVector();
	}

	@Override
	public DirectedAcyclicGraph<Integer, AConceptTransitionSet> getTransitionFunctionGraphOfActiveRepresentation() {
		if (activeState == null)
			return null;
		else return activeState.getTransitionFunction().asGraph();
	}

	@Override
	public double[][] getSimilarityMatrixOfActiveRepresentation() {
		if (activeState == null)
			return null;
		else return activeState.getDescription().getSimilarityMetrics().getSimilarityMatrix();
	}

	@Override
	public List<IContextObject> getContext() {
		return context;
	}

	@Override
	public Boolean explore(int representationID) {
		if (activeState != null && activeState.iD() == representationID)
			return false;
		boolean representationFound = false;
		Iterator<IRepresentation> repIte = problemSpaceExplorer.getProblemSpaceGraph().vertexSet().iterator();
		while (!representationFound && repIte.hasNext()) {
			IRepresentation nextRep = repIte.next();
			if (nextRep.iD() == representationID) {
				activeState = nextRep;
				representationFound = true;
			}
		}
		if (!representationFound)
			return null;
		return true;
	}

	@Override
	public Boolean expand(int representationID) {
		return problemSpaceExplorer.apply(representationID);
	}

	@Override
	public Integer getActiveRepresentationID() {
		if (activeState == null)
			return null;
		return activeState.iD();
	}

	@Override
	public Map<Integer, Set<IFact>> particularToAcceptedFactsInActiveRepresentation() {
		if (activeState == null)
			return null;
		return activeState.mapParticularIDsToAcceptedFacts();
	}

}
