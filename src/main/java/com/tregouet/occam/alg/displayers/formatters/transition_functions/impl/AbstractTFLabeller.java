package com.tregouet.occam.alg.displayers.formatters.transition_functions.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.displayers.formatters.transition_functions.TransitionFunctionLabeller;
import com.tregouet.occam.data.problem_space.states.transitions.AConceptTransitionSet;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.problem_space.states.transitions.TransitionType;
import com.tregouet.occam.data.problem_space.states.transitions.impl.ConceptTransitionSet;

public abstract class AbstractTFLabeller implements TransitionFunctionLabeller {

	private static final String nL = System.lineSeparator();

	private Set<Integer> vertices = new HashSet<>();
	private Map<Integer, Integer> targetIDToSourceID = new HashMap<>();
	private Map<Integer, Set<IConceptTransition>> targetIDToProductiveTrans = new HashMap<>();
	private Map<Integer, Set<IConceptTransition>> targetIDToOtherTransitions = new HashMap<>();

	public AbstractTFLabeller() {
	}

	private static String toString(boolean productive, Set<IConceptTransition> transitions) {
		StringBuilder sB = new StringBuilder();
		if (!transitions.isEmpty()) {
			if (productive) {
				sB.append("PRODUCTIVE TRANSITIONS : " + nL);
			} else
				sB.append("OTHERS : " + nL);
			Iterator<IConceptTransition> transIte = transitions.iterator();
			while (transIte.hasNext()) {
				sB.append(transIte.next().toString());
				if (transIte.hasNext())
					sB.append(nL);
			}
		}
		return sB.toString();
	}

	@Override
	public DirectedAcyclicGraph<Integer, AConceptTransitionSet> apply(IRepresentationTransitionFunction transFunc) {
		DirectedAcyclicGraph<Integer, AConceptTransitionSet> transFuncGraph = new DirectedAcyclicGraph<>(null, null,
				false);
		for (IConceptTransition transition : transFunc.getTransitions()) {
			Integer targetID = transition.getOutputInternConfiguration().getOutputStateID();
			if (!targetIDToSourceID.containsKey(targetID)) {
				vertices.add(targetID);
				Integer sourceID = transition.getInputConfiguration().getInputStateID();
				vertices.add(sourceID);
				targetIDToSourceID.put(targetID, sourceID);
				targetIDToProductiveTrans.put(targetID, new HashSet<>());
				targetIDToOtherTransitions.put(targetID, new HashSet<>());
			}
		}
		for (IConceptTransition transition : complyToOptionalConstraint(transFunc.getTransitions())) {
			Integer targetID = transition.getOutputInternConfiguration().getOutputStateID();
			if (transition.type() == TransitionType.PRODUCTIVE_TRANS)
				targetIDToProductiveTrans.get(targetID).add(transition);
			else
				targetIDToOtherTransitions.get(targetID).add(transition);
		}
		Graphs.addAllVertices(transFuncGraph, vertices);
		for (Integer targetID : targetIDToSourceID.keySet())
			transFuncGraph.addEdge(targetIDToSourceID.get(targetID), targetID, buildEdge(targetID));
		return transFuncGraph;
	}

	protected abstract Set<IConceptTransition> complyToOptionalConstraint(Set<IConceptTransition> transitions);

	private AConceptTransitionSet buildEdge(Integer targetID) {
		Set<IConceptTransition> transitions = new HashSet<>();
		StringBuilder sB = new StringBuilder();
		Set<IConceptTransition> productiveTransitions = targetIDToProductiveTrans.get(targetID);
		sB.append(toString(true, productiveTransitions));
		Set<IConceptTransition> others = targetIDToOtherTransitions.get(targetID);
		if (!others.isEmpty())
			sB.append(nL);
		sB.append(toString(false, others));
		transitions.addAll(productiveTransitions);
		transitions.addAll(others);
		Integer sourceID = targetIDToSourceID.get(targetID);
		return new ConceptTransitionSet(sourceID, targetID, transitions, sB.toString());
	}

}
