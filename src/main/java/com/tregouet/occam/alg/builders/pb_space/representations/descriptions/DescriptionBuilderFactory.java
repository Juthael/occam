package com.tregouet.occam.alg.builders.pb_space.representations.descriptions;

import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.impl.BuildTreeThenCalculateMetrics;

public class DescriptionBuilderFactory {

	public static final DescriptionBuilderFactory INSTANCE = new DescriptionBuilderFactory();

	private DescriptionBuilderFactory() {
	}

	public DescriptionBuilder apply(DescriptionBuilderStrategy strategy) {
		switch (strategy) {
		case BUILD_TREE_THEN_CALCULATE_METRICS:
			return BuildTreeThenCalculateMetrics.INSTANCE;
		default:
			return null;
		}
	}

}
