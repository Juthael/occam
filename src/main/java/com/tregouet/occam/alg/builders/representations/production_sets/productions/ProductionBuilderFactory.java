package com.tregouet.occam.alg.builders.representations.production_sets.productions;

import com.tregouet.occam.alg.builders.representations.production_sets.productions.impl.MapTargetVarsToSourceValues;
import com.tregouet.occam.alg.builders.representations.production_sets.productions.impl.SourceConceptCannotHaveTargetDenotation;

public class ProductionBuilderFactory {

	public static final ProductionBuilderFactory INSTANCE = new ProductionBuilderFactory();

	private ProductionBuilderFactory() {
	}

	public ProductionBuilder apply(ProductionBuilderStrategy strategy) {
		switch (strategy) {
		case MAP_TARGET_VARS_TO_SOURCE_VALUES:
			return new MapTargetVarsToSourceValues();
		case SRCE_CNCPT_CANNOT_HAVE_TGET_DENOT:
			return new SourceConceptCannotHaveTargetDenotation();
		default:
			return null;
		}
	}

}
