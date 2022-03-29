package com.tregouet.occam.data.logical_structures.scores.impl;

import com.tregouet.occam.data.logical_structures.scores.IScore;

public class DoubleScore implements IScore<DoubleScore> {
	
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
	
	@Override
	public String toString() {
		return Double.toString(value);
	}

}
