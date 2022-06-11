package com.tregouet.occam.data.problem_space.states.transitions;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.logical_structures.automata.transition_functions.IPushdownAutomatonTF;
import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.IApplication;

public interface IRepresentationTransitionFunction extends
		IPushdownAutomatonTF<IApplication, IBindings, IConceptTransitionIC, IConceptTransitionOIC, IConceptTransition> {
	
	DirectedAcyclicGraph<Integer, AConceptTransitionSet> asGraph();

}
