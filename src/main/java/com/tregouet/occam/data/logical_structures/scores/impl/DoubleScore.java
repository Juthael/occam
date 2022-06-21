package com.tregouet.occam.data.logical_structures.scores.impl;

import com.tregouet.occam.data.logical_structures.scores.IDoubleScore;
import com.tregouet.occam.data.logical_structures.scores.IScore;

public class DoubleScore implements IDoubleScore {

	private final double value;

	public DoubleScore(double value) {
		this.value = value;
	}

	@Override
	public int compareTo(IDoubleScore other) {
		return Double.compare(value, other.value());
	}

	@Override
	public String toString() {
		return IScore.round(value);
	}

	@Override
	public double value() {
		return value;
	}

}
