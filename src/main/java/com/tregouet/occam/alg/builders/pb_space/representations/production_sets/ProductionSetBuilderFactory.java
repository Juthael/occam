package com.tregouet.occam.alg.builders.pb_space.representations.production_sets;

import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.impl.BuildFromScratch;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.impl.NoEpsilonProd;

public class ProductionSetBuilderFactory {

	public static final ProductionSetBuilderFactory INSTANCE =
			new ProductionSetBuilderFactory();

	private ProductionSetBuilderFactory() {
	}

	public ProductionSetBuilder apply(ProductionSetBuilderStrategy strategy) {
		switch (strategy) {
		case BUILD_FROM_SCRATCH :
			return new BuildFromScratch();
		case NO_EPSILON : 
			return new NoEpsilonProd();
		default :
			return null;
		}
	}

}
