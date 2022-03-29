package com.tregouet.occam.data.logical_structures.scores;

public interface IScore<S extends IScore<S>> extends Comparable<S> {
	
	String toString();

}
