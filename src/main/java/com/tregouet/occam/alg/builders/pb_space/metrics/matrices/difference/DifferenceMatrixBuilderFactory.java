package com.tregouet.occam.alg.builders.pb_space.metrics.matrices.difference;

import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.difference.impl.TwoLeavesTreeForEachPair;

public class DifferenceMatrixBuilderFactory {

	public static final DifferenceMatrixBuilderFactory INSTANCE = new DifferenceMatrixBuilderFactory();

	private DifferenceMatrixBuilderFactory() {
	}

	public DifferenceMatrixBuilder apply(DifferenceMatrixBuilderStrategy strategy) {
		switch(strategy) {
		case TWO_LEAVES_TREE_FOR_EACH_PAIR :
			return TwoLeavesTreeForEachPair.INSTANCE;
		default :
			return null;
		}
	}

}
