package com.tregouet.occam.alg.builders.representations.descriptions.differentiae;

import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.impl.WithDifferentiation;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.impl.WithoutDifferentiation;

public class DifferentiaeBuilderFactory {

	public static final DifferentiaeBuilderFactory INSTANCE = new DifferentiaeBuilderFactory();

	private DifferentiaeBuilderFactory() {
	}

	public DifferentiaeBuilder apply(DifferentiaeBuilderStrategy strategy) {
		switch (strategy) {
		case WITH_DIFFERENTIATION :
			return WithDifferentiation.INSTANCE;
		case WITHOUT_DIFFERENTIATION :
			return WithoutDifferentiation.INSTANCE;
		default:
			return null;
		}
	}

}
