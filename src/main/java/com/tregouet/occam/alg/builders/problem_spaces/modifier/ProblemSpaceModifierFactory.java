package com.tregouet.occam.alg.builders.problem_spaces.modifier;

import com.tregouet.occam.alg.builders.problem_spaces.modifier.impl.RebuildFromScratch;

public class ProblemSpaceModifierFactory {
	
	public static final ProblemSpaceModifierFactory INSTANCE = new ProblemSpaceModifierFactory();
	
	private ProblemSpaceModifierFactory() {
	}
	public ProblemSpaceModifier apply(ProblemSpaceModifierStrategy strategy) {
		switch (strategy) {
			case REBUILD_FROM_SCRATCH : 
				return RebuildFromScratch.INSTANCE;
			default : 
				return null;
		}
	}

}
