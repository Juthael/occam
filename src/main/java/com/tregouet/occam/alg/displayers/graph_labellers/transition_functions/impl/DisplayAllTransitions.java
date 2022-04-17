package com.tregouet.occam.alg.displayers.graph_labellers.transition_functions.impl;

import java.util.Set;

import com.tregouet.occam.alg.displayers.graph_labellers.transition_functions.TransitionFunctionLabeller;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;

public class DisplayAllTransitions extends AbstractTFLabeller implements TransitionFunctionLabeller {

	@Override
	protected Set<IConceptTransition> filter(Set<IConceptTransition> transitions) {
		return transitions;
	}

}
