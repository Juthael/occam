package com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.difference;

import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.difference.impl.TwoLeavesTreeForEachPairDEP;

public class DifferenceMatrixBuilderFactoryDEP {

	public static final DifferenceMatrixBuilderFactoryDEP INSTANCE = new DifferenceMatrixBuilderFactoryDEP();

	private DifferenceMatrixBuilderFactoryDEP() {
	}

	public DifferenceMatrixBuilderDEP apply(DifferenceMatrixBuilderStrategyDEP strategy) {
		switch(strategy) {
		case TWO_LEAVES_TREE_FOR_EACH_PAIR :
			return TwoLeavesTreeForEachPairDEP.INSTANCE;
		default :
			return null;
		}
	}

}
