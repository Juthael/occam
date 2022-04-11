package com.tregouet.occam.data.logical_structures.orders.total.impl;

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
		}
		else comparison = compareTo(o.values.get(0));
		if (comparison == 0)
			//for consistency with Object.equals(), which is not overloaded 
			return System.identityHashCode(this) - System.identityHashCode(o);
		return comparison;
	}
	
	@Override
	public String toString() {
		return Double.toString(value);
	}	
	
	private int compareTo(double otherValue) {
		return Double.compare(this.value, otherValue);
	}

}
