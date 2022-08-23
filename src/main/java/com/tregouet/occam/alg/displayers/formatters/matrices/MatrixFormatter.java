package com.tregouet.occam.alg.displayers.formatters.matrices;

public interface MatrixFormatter {
	
	String[][] apply(Double[][] matrix);
	
	String[] apply(double[] vector);

}
