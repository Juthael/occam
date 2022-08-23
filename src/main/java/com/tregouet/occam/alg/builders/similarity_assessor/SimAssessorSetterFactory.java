package com.tregouet.occam.alg.builders.similarity_assessor;

import com.tregouet.occam.alg.builders.similarity_assessor.impl.SystemicPressure;

public class SimAssessorSetterFactory {
	
	public static final SimAssessorSetterFactory INSTANCE = new SimAssessorSetterFactory();
	
	private SimAssessorSetterFactory() {
	}
	
	public SimAssessorSetter apply(SimAssessorSetterStrategy strategy) {
		switch (strategy) {
		case SYSTEMIC_PRESSURE : 
			return new SystemicPressure();
		default : 
			return null;
		}
	}

}
