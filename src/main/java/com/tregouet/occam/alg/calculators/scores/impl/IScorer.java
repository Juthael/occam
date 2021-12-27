package com.tregouet.occam.alg.calculators.scores.impl;

import com.tregouet.occam.alg.calculators.scores.IRated;

public interface IScorer<S extends IScorer<S, R>, R extends IRated> {
	
	S input(R rated);
	
	void setScore();

}
