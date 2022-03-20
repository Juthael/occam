package com.tregouet.occam.alg.scorers;

public interface IWeigher<W extends IWeighed> {
	
	void setCost(W wheighed);

}
