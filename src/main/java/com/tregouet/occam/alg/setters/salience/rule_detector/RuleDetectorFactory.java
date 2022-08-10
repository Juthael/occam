package com.tregouet.occam.alg.setters.salience.rule_detector;

import com.tregouet.occam.alg.setters.salience.rule_detector.impl.VarInstantiationCompleteAndDistinctive;

public class RuleDetectorFactory {

	public static final RuleDetectorFactory INSTANCE = new RuleDetectorFactory();

	private RuleDetectorFactory() {
	}

	public RuleDetector apply(RuleDetectorStrategy strategy) {
		switch (strategy) {
		case INST_COMPLETE_AND_DISTINCTIVE :
			return VarInstantiationCompleteAndDistinctive.INSTANCE;
		default :
			return null;
		}
	}

}
