package com.tregouet.occam.alg.scores_calc;

public interface IWeigher<W extends IWeighed> {
	
	void setCost(W wheighed);

}
