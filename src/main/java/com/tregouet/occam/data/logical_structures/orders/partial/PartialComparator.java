package com.tregouet.occam.data.logical_structures.orders.partial;

@FunctionalInterface
public interface PartialComparator<T> {
	
	/**
	 * 
	 * @param o1
	 * @param o2
	 * @return a negative Integer, zero, a positive Integer or null as o1
     *          is less than, equal to, greater than, or incomparable with o2.
	 */
	Integer compare(T o1, T o2);

}
