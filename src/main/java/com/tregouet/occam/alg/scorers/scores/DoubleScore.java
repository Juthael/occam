package com.tregouet.occam.alg.scorers.scores;

import com.tregouet.occam.alg.scorers.Score;

public class DoubleScore implements Score<DoubleScore> {
	
	private final double value;
	
	public DoubleScore(double value) {
		this.value = value;
	}
	
	public double value() {
		return value;
	}

	@Override
	public int compareTo(DoubleScore other) {
		return Double.compare(value, other.value());
	}

}
