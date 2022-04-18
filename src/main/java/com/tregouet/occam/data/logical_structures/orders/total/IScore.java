package com.tregouet.occam.data.logical_structures.orders.total;

import java.text.DecimalFormat;

public interface IScore<S extends IScore<S>> extends Comparable<S> {

	public static final DecimalFormat df = new DecimalFormat("#.####");

	public static String round(double nb) {
		return df.format(nb).toString();
	}

	@Override
	String toString();

}
