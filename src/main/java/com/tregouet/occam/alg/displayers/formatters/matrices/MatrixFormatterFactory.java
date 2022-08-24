package com.tregouet.occam.alg.displayers.formatters.matrices;

import com.tregouet.occam.alg.displayers.formatters.matrices.impl.ThreeDecimals;

public class MatrixFormatterFactory {

	public static final MatrixFormatterFactory INSTANCE = new MatrixFormatterFactory();

	private MatrixFormatterFactory() {
	}

	public MatrixFormatter apply(MatrixFormatterStrategy strategy) {
		switch(strategy) {
		case THREE_DECIMALS :
			return ThreeDecimals.INSTANCE;
		default :
			return null;
		}
	}

}
