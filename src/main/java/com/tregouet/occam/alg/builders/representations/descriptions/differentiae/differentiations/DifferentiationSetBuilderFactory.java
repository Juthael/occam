package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations;

import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.impl.HeuristicOrderOverProperties;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.impl.RemoveRedundantPermutations;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.impl.TryEveryPermutation;

public class DifferentiationSetBuilderFactory {

	public static final DifferentiationSetBuilderFactory INSTANCE = new DifferentiationSetBuilderFactory();

	private DifferentiationSetBuilderFactory() {
	}

	public DifferentiationSetBuilder apply(DifferentiationSetBuilderStrategy strategy) {
		switch (strategy) {
		case TRY_EVERY_PERMUTATION :
			return new TryEveryPermutation();
		case REMOVE_REDUNDANT_PERMUTATIONS :
			return new RemoveRedundantPermutations();
		case HEURISTIC_ORDER_OVER_PROPERTIES :
			return HeuristicOrderOverProperties.INSTANCE;
		default :
			return null;
		}
	}

}
