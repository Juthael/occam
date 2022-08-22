package com.tregouet.occam.data.representations.transitions;

import com.tregouet.occam.data.representations.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;
import com.tregouet.occam.data.structures.automata.transition_functions.transitions.IPushdownAutomatonTransition;
import com.tregouet.occam.data.structures.lambda_terms.IBindings;

public interface IConceptTransition extends
		IPushdownAutomatonTransition<IAbstractionApplication, IBindings, IConceptTransitionIC, IConceptTransitionOIC> {

	@Override
	String toString();

	TransitionType type();

}
