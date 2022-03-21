package com.tregouet.occam.alg.scorers;

public interface IWeigher<W extends IWeighed> {
	
	double weigh(W weighed);

}
