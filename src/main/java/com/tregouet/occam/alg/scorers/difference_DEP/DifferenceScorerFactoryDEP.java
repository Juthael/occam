package com.tregouet.occam.alg.scorers.difference_DEP;

import com.tregouet.occam.alg.scorers.difference_DEP.impl.TwoLeafTreeDEP;

public class DifferenceScorerFactoryDEP {

	public static final DifferenceScorerFactoryDEP INSTANCE = new DifferenceScorerFactoryDEP();

	private DifferenceScorerFactoryDEP() {
	}

	public DifferenceScorerDEP apply(DifferenceScorerStrategyDEP strategy) {
		switch (strategy) {
		case TWO_LEAF_TREE :
			return TwoLeafTreeDEP.INSTANCE;
		default :
			return null;
		}
	}

}
