package com.tregouet.occam.data.problem_space.states.transitions;

import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.IPushdownAutomatonTransition;
import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.IApplication;

public interface IConceptTransition extends
		IPushdownAutomatonTransition<IApplication, IBindings, IConceptTransitionIC, IConceptTransitionOIC> {

	@Override
	String toString();

	TransitionType type();

}
