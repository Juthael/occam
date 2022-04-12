package com.tregouet.occam.alg.builders.problem_spaces;

import com.tregouet.occam.alg.builders.problem_spaces.impl.GaloisLatticeOfRepresentations;

public class ProblemSpaceBuilderFactory {
	
	public static final ProblemSpaceBuilderFactory INSTANCE = 
			new ProblemSpaceBuilderFactory();
	
	private ProblemSpaceBuilderFactory() {
	}
	
	public ProblemSpaceBuilder apply(ProblemSpaceBuilderStrategy strategy) {
		switch(strategy) {
			case GALOIS_LATTICE_OF_REPRESENTATIONS : 
				return GaloisLatticeOfRepresentations.INSTANCE;
			default : 
				return null;
		}
	}

}
