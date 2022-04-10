package com.tregouet.occam.alg.builders.problem_spaces;

import com.tregouet.occam.alg.builders.problem_spaces.impl.GaloisLatticeOfRepresentations;

public class CategorizationProblemSpaceBuilderFactory {
	
	public static final CategorizationProblemSpaceBuilderFactory INSTANCE = 
			new CategorizationProblemSpaceBuilderFactory();
	
	private CategorizationProblemSpaceBuilderFactory() {
	}
	
	public CategorizationProblemSpaceBuilder apply(CategorizationProblemSpaceBuilderStrategy strategy) {
		switch(strategy) {
			case GALOIS_LATTICE_OF_REPRESENTATIONS : 
				return GaloisLatticeOfRepresentations.INSTANCE;
			default : 
				return null;
		}
	}

}
