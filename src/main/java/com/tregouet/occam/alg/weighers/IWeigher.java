package com.tregouet.occam.alg.weighers;

public interface IWeigher<W extends IWeighed> {
	
	double weigh(W weighed);

}
