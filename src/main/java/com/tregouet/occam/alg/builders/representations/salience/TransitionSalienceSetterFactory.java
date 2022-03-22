package com.tregouet.occam.alg.builders.representations.salience;

import com.tregouet.occam.alg.builders.representations.salience.impl.HiddenByDefaultThenFindSpecifics;

public class TransitionSalienceSetterFactory {
	
	public static final TransitionSalienceSetterFactory INSTANCE = new TransitionSalienceSetterFactory();
	
	private TransitionSalienceSetterFactory() {
	}
	
	public ITransitionSalienceSetter apply(TransitionSalienceSettingStrategy strategy) {
		switch (strategy) {
			case HIDDEN_BY_DEFAULT_THEN_FIND_SPECIFICS : 
				return new HiddenByDefaultThenFindSpecifics();
			default : 
				return null;
		}
	}

}
