package com.tregouet.occam.data.logical_structures.scores;

public interface IDoubleScore extends IScore<IDoubleScore> {

	@Override
	int compareTo(IDoubleScore other);

	@Override
	String toString();

	double value();

}