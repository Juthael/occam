package com.tregouet.occam.alg.builders.comparator;

import com.tregouet.occam.alg.builders.comparator.impl.SystemicPressure;

public class ComparatorSetterFactory {

	public static final ComparatorSetterFactory INSTANCE = new ComparatorSetterFactory();

	private ComparatorSetterFactory() {
	}

	public ComparatorSetter apply(ComparatorSetterStrategy strategy) {
		switch (strategy) {
		case SYSTEMIC_PRESSURE :
			return new SystemicPressure();
		default :
			return null;
		}
	}

}
