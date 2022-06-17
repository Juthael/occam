package com.tregouet.occam.data.problem_space.states.transitions;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.logical_structures.automata.transition_functions.IPushdownAutomatonTF;
import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;

public interface IRepresentationTransitionFunction extends
		IPushdownAutomatonTF<IAbstractionApplication, IBindings, IConceptTransitionIC, IConceptTransitionOIC, IConceptTransition> {

	DirectedAcyclicGraph<Integer, AConceptTransitionSet> asGraph();

}
