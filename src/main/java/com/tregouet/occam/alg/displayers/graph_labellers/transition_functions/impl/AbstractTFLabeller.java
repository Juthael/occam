package com.tregouet.occam.alg.displayers.graph_labellers.transition_functions.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.displayers.graph_labellers.transition_functions.TransitionFunctionLabeller;
import com.tregouet.occam.data.representations.transitions.AConceptTransitionSet;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.representations.transitions.TransitionType;
import com.tregouet.occam.data.representations.transitions.impl.ConceptTransitionSet;

public abstract class AbstractTFLabeller implements TransitionFunctionLabeller {

	private static final String nL = System.lineSeparator();
	private Set<Integer> vertices = new HashSet<>();
	private Map<Integer, Integer> targetIDToSourceID = new HashMap<>();
	private Map<Integer, Set<IConceptTransition>> targetIDToApplications = new HashMap<>();
	private Map<Integer, Set<IConceptTransition>> targetIDToOtherTransitions = new HashMap<>();
	
	public AbstractTFLabeller() {
	}
	
	@Override
	public DirectedAcyclicGraph<Integer, AConceptTransitionSet> apply(
			IRepresentationTransitionFunction transFunc) {
		DirectedAcyclicGraph<Integer, AConceptTransitionSet> transFuncGraph = new DirectedAcyclicGraph<>(null, null, false);
		for (IConceptTransition transition : transFunc.getTransitions()) {
			Integer targetID = transition.getOutputInternConfiguration().getOutputStateID();
			if (!targetIDToSourceID.containsKey(targetID)) {
				vertices.add(targetID);
				Integer sourceID = transition.getInputConfiguration().getInputStateID();
				vertices.add(sourceID);
				targetIDToSourceID.put(targetID, sourceID);
				targetIDToApplications.put(targetID, new HashSet<>());
				targetIDToOtherTransitions.put(targetID, new HashSet<>());
			}
		}
		for (IConceptTransition transition : filter(transFunc.getTransitions())) {
			Integer targetID = transition.getOutputInternConfiguration().getOutputStateID();
			if (transition.type() == TransitionType.APPLICATION)
				targetIDToApplications.get(targetID).add(transition);
			else targetIDToOtherTransitions.get(targetID).add(transition);
		}
		Graphs.addAllVertices(transFuncGraph, vertices);
		for (Integer targetID : targetIDToSourceID.keySet())
			transFuncGraph.addEdge(targetIDToSourceID.get(targetID), targetID, buildEdge(targetID));
		return transFuncGraph;
	}
	
	protected abstract Set<IConceptTransition> filter(Set<IConceptTransition> transitions);
	
	private AConceptTransitionSet buildEdge(Integer targetID) {
		Set<IConceptTransition> transitions = new HashSet<>();
		StringBuilder sB = new StringBuilder();
		Set<IConceptTransition> applications = targetIDToApplications.get(targetID);
		sB.append(toString(true, applications));
		Set<IConceptTransition> others = targetIDToOtherTransitions.get(targetID);
		if (!others.isEmpty())
			sB.append(nL);
		sB.append(toString(false, others));
		transitions.addAll(applications);
		transitions.addAll(others);
		Integer sourceID = targetIDToSourceID.get(targetID);
		return new ConceptTransitionSet(sourceID, targetID, transitions, sB.toString());
	}
	
	private static  String toString(boolean application, Set<IConceptTransition> transitions) {
		StringBuilder sB = new StringBuilder();
		if (!transitions.isEmpty()) {
			if (application) {
				sB.append("applications : " + nL);
			}
			else sB.append("others : " + nL);
			Iterator<IConceptTransition> transIte = transitions.iterator();
			while (transIte.hasNext()) {
				sB.append(transIte.next().toString());
				if (transIte.hasNext())
					sB.append(nL);
			}
		}
		return sB.toString();
	}

}
