package com.tregouet.occam.alg.builders.representations.descriptions.ranks;

import com.tregouet.occam.alg.builders.representations.descriptions.ranks.impl.DepthFirstRankSetter;

public class DifferentiaeRankSetterFactory {

	public static final DifferentiaeRankSetterFactory INSTANCE = new DifferentiaeRankSetterFactory();

	private DifferentiaeRankSetterFactory() {
	}

	public DifferentiaeRankSetter apply (DifferentiaeRankSetterStrategy strategy) {
		switch (strategy) {
			case DEPTH_FIRST :
				return DepthFirstRankSetter.INSTANCE;
			default :
				return null;
		}
	}

}
