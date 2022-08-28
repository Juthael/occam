package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.differentiation;

import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.differentiation.impl.MuteRedundanciesThenBuildThenWeigh;

public class DifferentiationBuilderFactory {

	public static final DifferentiationBuilderFactory INSTANCE = new DifferentiationBuilderFactory();

	private DifferentiationBuilderFactory() {
	}

	public DifferentiationBuilder apply(DifferentiationBuilderStrategy strategy) {
		switch (strategy) {
		case MUTE_REDUNDANCIES_THEN_WEIGHT :
			return new MuteRedundanciesThenBuildThenWeigh();
		case USELESS :
			return null;
		default :
			return null;
		}
	}

}
