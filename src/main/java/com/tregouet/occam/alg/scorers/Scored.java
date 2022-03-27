package com.tregouet.occam.alg.scorers;

public interface Scored<S extends Score<S>> {
	
	S score();
	
	void setScore(S score);

}
