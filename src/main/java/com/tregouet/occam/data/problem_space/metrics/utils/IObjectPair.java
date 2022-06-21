package com.tregouet.occam.data.problem_space.metrics.utils;

import com.tregouet.occam.alg.scorers.Scored;
import com.tregouet.occam.data.logical_structures.scores.impl.IDoubleScore;

public interface IObjectPair extends Scored<IDoubleScore> {

	Integer getFirstObjectID();

	Integer getSecondObjectID();

}
