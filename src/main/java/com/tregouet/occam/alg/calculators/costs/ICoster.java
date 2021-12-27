package com.tregouet.occam.alg.calculators.costs;

public interface ICoster<C extends ICoster<C, D>, D extends ICosted> {
	
	C input(ICosted costed);
	
	void setCost();

}
