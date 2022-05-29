package com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.transition_saliences;

import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.transition_saliences.impl.EveryTransitionIsRule;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.transition_saliences.impl.HiddenByDefaultThenFindSpecifics;

public class TransitionSalienceSetterFactory {

	public static final TransitionSalienceSetterFactory INSTANCE = new TransitionSalienceSetterFactory();

	private TransitionSalienceSetterFactory() {
	}

	public TransitionSalienceSetter apply(TransitionSalienceSetterStrategy strategy) {
		switch (strategy) {
		case HIDDEN_BY_DEFAULT_THEN_FIND_SPECIFICS:
			return new HiddenByDefaultThenFindSpecifics();
		case EVERY_TRANSITION_IS_RULE : 
			return EveryTransitionIsRule.INSTANCE;
		default:
			return null;
		}
	}

}
