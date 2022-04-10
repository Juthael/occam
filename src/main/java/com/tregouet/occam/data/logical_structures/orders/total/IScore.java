package com.tregouet.occam.data.logical_structures.orders.total;

public interface IScore<S extends IScore<S>> extends Comparable<S> {
	
	String toString();

}
