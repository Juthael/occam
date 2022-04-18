package com.tregouet.occam;

import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.OverallStrategy;

public class Occam {

	public static final OverallStrategy strategy = OverallStrategy.OVERALL_STRATEGY_1;

	public Occam() {
	}

	public static void main(String[] args) {
		OverallAbstractFactory.INSTANCE.apply(strategy);
		new PrototypeMenu();
	}

}
