package com.tregouet.occam.alg.builders.pb_space.representations.lattice_productions;

import com.tregouet.occam.alg.builders.pb_space.representations.lattice_productions.impl.IfIsAThenBuildProductions;

public class ProductionBuilderFactory {

	public static final ProductionBuilderFactory INSTANCE = new ProductionBuilderFactory();

	private ProductionBuilderFactory() {
	}

	public ProductionBuilder apply(ProductionBuilderStrategy strategy) {
		switch (strategy) {
		case IF_SUBORDINATE_THEN_BUILD_PRODUCTIONS:
			return new IfIsAThenBuildProductions();
		default:
			return null;
		}
	}

}
