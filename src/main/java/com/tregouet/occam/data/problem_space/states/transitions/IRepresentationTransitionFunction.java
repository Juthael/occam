package com.tregouet.occam.data.problem_space.states.transitions;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.logical_structures.automata.transition_functions.IPushdownAutomatonTF;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.IApplication;

public interface IRepresentationTransitionFunction extends
		IPushdownAutomatonTF<IApplication, AVariable, IConceptTransitionIC, IConceptTransitionOIC, IConceptTransition> {
	
	DirectedAcyclicGraph<Integer, AConceptTransitionSet> asGraph();

}
