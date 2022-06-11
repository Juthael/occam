package com.tregouet.occam.data.logical_structures.scores;

import java.text.DecimalFormat;

public interface IScore<S extends IScore<S>> extends Comparable<S> {

	public static final DecimalFormat df = new DecimalFormat("#.####");

	@Override
	boolean equals(Object o);

	@Override
	int hashCode();

	@Override
	String toString();

	public static String round(double nb) {
		return df.format(nb).toString();
	}

}
