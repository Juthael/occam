package com.tregouet.occam.alg.calculators.scores;

public interface IScorer<S extends IScorer<S, R>, R extends IScored> {
	
	S input(R rated);
	
	void setScore();

}
