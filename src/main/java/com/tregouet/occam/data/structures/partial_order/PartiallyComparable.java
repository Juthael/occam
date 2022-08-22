package com.tregouet.occam.data.structures.partial_order;

public interface PartiallyComparable<T> {

	/**
	 *
	 * @param o
	 * @return a negative Integer, zero, a positive Integer or null as this object
	 *         is less than, equal to, greater than, or incomparable with the
	 *         specified object.
	 */
	public Integer compareTo(T o);

}
