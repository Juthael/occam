package com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.typicality;

import com.tregouet.occam.alg.builders.metricsDEP.matrices_DEP.typicality.impl.AsymmetricalSimMatrixRowAverageDEP;

public class TypicalityVectorBuilderFactoryDEP {

	public static final TypicalityVectorBuilderFactoryDEP INSTANCE = new TypicalityVectorBuilderFactoryDEP();

	private TypicalityVectorBuilderFactoryDEP() {
	}

	public TypicalityVectorBuilderDEP apply(TypicalityVectorBuilderStrategyDEP strategy) {
		switch (strategy) {
		case ASYMM_SIM_MATRIX_ROW_AVERAGE :
			return AsymmetricalSimMatrixRowAverageDEP.INSTANCE;
		default :
			return null;
		}
	}

}
