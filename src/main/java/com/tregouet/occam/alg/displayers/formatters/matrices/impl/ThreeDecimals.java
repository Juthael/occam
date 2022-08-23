package com.tregouet.occam.alg.displayers.formatters.matrices.impl;

import java.math.BigDecimal;
import java.math.MathContext;

import com.tregouet.occam.alg.displayers.formatters.matrices.MatrixFormatter;

public class ThreeDecimals implements MatrixFormatter {
	
	public static final ThreeDecimals INSTANCE = new ThreeDecimals();
	private static final MathContext mathContext = new MathContext(3);
	
	private ThreeDecimals() {
	}

	@Override
	public String[][] apply(Double[][] matrix) {
		int x = matrix.length;
		String[][] stringMatrix = new String[x][];
		for (int i = 0 ; i < x ; i++) {
			int y = matrix[i].length;
			stringMatrix[i] = new String[y];
			for (int j = 0 ; j < y ; j++) {
				Double dbleVal = matrix[i][j];
				if (dbleVal != null)
					stringMatrix[i][j] = new BigDecimal(dbleVal).round(mathContext).toString();
				else stringMatrix[i][j] = new String();
			}
		}
		return stringMatrix;
	}

	@Override
	public String[] apply(double[] vector) {
		String[] stringVector = new String[vector.length];
		for (int i = 0 ; i < vector.length ; i++) {
			stringVector[i] = new BigDecimal(vector[i]).round(mathContext).toString();
		}
		return stringVector;
	}

}
