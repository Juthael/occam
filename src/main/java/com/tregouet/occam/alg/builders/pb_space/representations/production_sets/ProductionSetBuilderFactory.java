package com.tregouet.occam.alg.builders.pb_space.representations.production_sets;

import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.impl.BuildFromScratch;

public class ProductionSetBuilderFactory {

	public static final ProductionSetBuilderFactory INSTANCE =
			new ProductionSetBuilderFactory();

	private ProductionSetBuilderFactory() {
	}

	public ProductionSetBuilder apply(ProductionSetBuilderStrategy strategy) {
		switch (strategy) {
		case BUILD_FROM_SCRATCH :
			return BuildFromScratch.INSTANCE;
		default :
			return null;
		}
	}

}
