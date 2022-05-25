package com.tregouet.occam.alg.builders.pb_space.ranker;

import com.tregouet.occam.alg.builders.pb_space.ranker.impl.TopDownRanker;

public class ProblemTransitionRankerFactory {
	
	public static final ProblemTransitionRankerFactory INSTANCE = new ProblemTransitionRankerFactory();
	
	private ProblemTransitionRankerFactory() {
	}
	
	public ProblemTransitionRanker apply(ProblemTransitionRankerStrategy strategy) {
		switch (strategy) {
			case TOP_DOWN_RANKER : 
				return new TopDownRanker();
			default : 
				return null;
		}
	}

}
