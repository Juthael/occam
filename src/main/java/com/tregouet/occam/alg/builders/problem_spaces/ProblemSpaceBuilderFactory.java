package com.tregouet.occam.alg.builders.problem_spaces;

import com.tregouet.occam.alg.builders.problem_spaces.impl.GaloisLatticeOfRepresentations;
import com.tregouet.occam.alg.builders.problem_spaces.impl.GaloisLatticeWithNoDumbState;

public class ProblemSpaceBuilderFactory {

	public static final ProblemSpaceBuilderFactory INSTANCE = new ProblemSpaceBuilderFactory();

	private ProblemSpaceBuilderFactory() {
	}

	public ProblemSpaceBuilder apply(ProblemSpaceBuilderStrategy strategy) {
		switch (strategy) {
			case GALOIS_LATTICE_OF_REPRESENTATIONS :
				return new GaloisLatticeOfRepresentations();
			case GALOIS_LATTICE_WITH_NO_DUMB_STATE :
				return new GaloisLatticeWithNoDumbState();
			default:
				return null;
		}
	}

}
