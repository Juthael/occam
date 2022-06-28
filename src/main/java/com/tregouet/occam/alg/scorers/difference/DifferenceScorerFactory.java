package com.tregouet.occam.alg.scorers.difference;

import com.tregouet.occam.alg.scorers.difference.impl.TwoLeafTree;

public class DifferenceScorerFactory {

	public static final DifferenceScorerFactory INSTANCE = new DifferenceScorerFactory();

	private DifferenceScorerFactory() {
	}

	public DifferenceScorer apply(DifferenceScorerStrategy strategy) {
		switch (strategy) {
		case TWO_LEAF_TREE :
			return TwoLeafTree.INSTANCE;
		default :
			return null;
		}
	}

}
