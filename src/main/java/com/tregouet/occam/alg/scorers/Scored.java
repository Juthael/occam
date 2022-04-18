package com.tregouet.occam.alg.scorers;

import com.tregouet.occam.data.logical_structures.orders.total.IScore;

public interface Scored<S extends IScore<S>> {

	S score();

	void setScore(S score);

}
