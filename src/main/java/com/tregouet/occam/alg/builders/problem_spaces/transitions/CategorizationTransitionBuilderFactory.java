package com.tregouet.occam.alg.builders.problem_spaces.transitions;

import com.tregouet.occam.alg.builders.problem_spaces.transitions.impl.UsePartialOrder;

public class CategorizationTransitionBuilderFactory {
	
	public static final CategorizationTransitionBuilderFactory INSTANCE = 
			new CategorizationTransitionBuilderFactory();
	
	private CategorizationTransitionBuilderFactory() {
	}
	
	public CategorizationTransitionBuilder apply(CategorizationTransitionBuilderStrategy strategy) {
		switch (strategy) {
			case USE_PARTIAL_ORDER : 
				return UsePartialOrder.INSTANCE;
			default : 
				return null;
		}
	}

}
