package com.tregouet.occam.alg.builders.representations.descriptions.differentiae;

import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.impl.IfIsAThenDiffer;

public class DifferentiaeBuilderFactory {

	public static final DifferentiaeBuilderFactory INSTANCE = new DifferentiaeBuilderFactory();

	private DifferentiaeBuilderFactory() {
	}

	public DifferentiaeBuilder apply(DifferentiaeBuilderStrategy strategy) {
		switch (strategy) {
		case IF_IS_A_THEN_DIFFER:
			return new IfIsAThenDiffer();
		default:
			return null;
		}
	}

}
