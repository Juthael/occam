package com.tregouet.occam.alg.scoring_dep.costs;

public interface ICoster<C extends ICoster<C, D>, D extends ICosted> {
	
	C input(D costed);
	
	void setCost();

}
