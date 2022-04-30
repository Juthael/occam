package com.tregouet.occam.data.logical_structures.orders.total.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import com.tregouet.occam.data.logical_structures.orders.total.IScore;

public class Size1LecticScore extends LecticScore implements IScore<LecticScore> {

	private double value;

	public Size1LecticScore(double value) {
		super(null);
		this.value = value;
	}

	@Override
	public int compareTo(LecticScore o) {
		int comparison;
		if (o instanceof Size1LecticScore) {
			Size1LecticScore other = (Size1LecticScore) o;
			comparison = compareTo(other.value);
		} else
			comparison = compareTo(o.values.get(0));
		return comparison;
	}

	@Override
	public String toString() {
		return IScore.round(value);
	}

	private int compareTo(double otherValue) {
		return Double.compare(this.value, otherValue);
	}
	
	@Override
	public int hashCode() {
		//so that if that if a LecticScore equals a Size1LecticScore, they return the same hashCode.
		return Objects.hash(new ArrayList<Double>(Arrays.asList(new Double[] {value})));
	}
	
	public double get() {
		return value;
	}

}
