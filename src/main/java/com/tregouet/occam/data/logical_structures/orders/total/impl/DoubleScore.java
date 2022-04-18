package com.tregouet.occam.data.logical_structures.orders.total.impl;

import com.tregouet.occam.data.logical_structures.orders.total.IScore;

public class DoubleScore implements IScore<DoubleScore> {

	private final double value;

	public DoubleScore(double value) {
		this.value = value;
	}

	@Override
	public int compareTo(DoubleScore other) {
		return Double.compare(value, other.value());
	}

	@Override
	public String toString() {
		return IScore.round(value);
	}

	public double value() {
		return value;
	}

}
