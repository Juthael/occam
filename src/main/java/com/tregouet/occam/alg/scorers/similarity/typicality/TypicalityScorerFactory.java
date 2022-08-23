package com.tregouet.occam.alg.scorers.similarity.typicality;

import com.tregouet.occam.alg.scorers.similarity.typicality.impl.HowMuchAreOthersSimilarToIt;

public class TypicalityScorerFactory {
	
	public static final TypicalityScorerFactory INSTANCE = new TypicalityScorerFactory();
	
	private TypicalityScorerFactory() {
	}
	
	public TypicalityScorer apply(TypicalityScorerStrategy strategy) {
		switch(strategy) {
		case HOW_MUCH_ARE_OTHERS_SIMILAR : 
			return HowMuchAreOthersSimilarToIt.INSTANCE;
		default : 
			return null;
		}
	}

}
