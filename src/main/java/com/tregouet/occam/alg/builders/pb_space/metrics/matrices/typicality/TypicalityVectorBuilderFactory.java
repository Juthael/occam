package com.tregouet.occam.alg.builders.pb_space.metrics.matrices.typicality;

import com.tregouet.occam.alg.builders.pb_space.metrics.matrices.typicality.impl.AsymmetricalSimMatrixRowAverage;

public class TypicalityVectorBuilderFactory {
	
	public static final TypicalityVectorBuilderFactory INSTANCE = new TypicalityVectorBuilderFactory();
	
	private TypicalityVectorBuilderFactory() {
	}
	
	public TypicalityVectorBuilder apply(TypicalityVectorBuilderStrategy strategy) {
		switch (strategy) {
		case ASYMM_SIM_MATRIX_ROW_AVERAGE : 
			return AsymmetricalSimMatrixRowAverage.INSTANCE;
		default : 
			return null;
		}
	}

}
