package com.tregouet.occam.alg.builders.representations.production_sets;

import com.tregouet.occam.alg.builders.representations.production_sets.impl.BuildFromScratchNoEpsilon;
import com.tregouet.occam.alg.builders.representations.production_sets.impl.BuildFromScratchWildTransitions;
import com.tregouet.occam.alg.builders.representations.production_sets.impl.NoEpsilonProdWildTransitions;

public class ProductionSetBuilderFactory {

	public static final ProductionSetBuilderFactory INSTANCE =
			new ProductionSetBuilderFactory();

	private ProductionSetBuilderFactory() {
	}

	public ProductionSetBuilder apply(ProductionSetBuilderStrategy strategy) {
		switch (strategy) {
		case BUILD_FROM_SCRATCH_NO_EPSILON :
			return new BuildFromScratchNoEpsilon();
		case BUILD_FROM_SCRATCH_WILD :
			return new BuildFromScratchWildTransitions();
		case NO_EPSILON_WILD :
			return new NoEpsilonProdWildTransitions();
		default :
			return null;
		}
	}

}
