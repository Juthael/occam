package com.tregouet.occam.alg.scorers;

import com.tregouet.occam.data.logical_structures.scores.IScore;

public interface Scored<S extends IScore<S>> {
	
	S score();
	
	void setScore(S score);

}
