package com.tregouet.occam.alg.displayers.formatters.matrices;

public interface MatrixFormatter {

	String[] apply(double[] vector);

	String[][] apply(Double[][] matrix);

}
