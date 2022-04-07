package com.tregouet.occam.data.representations;

import com.tregouet.occam.alg.scorers.Scored;
import com.tregouet.occam.data.logical_structures.scores.impl.LexicographicScore;
import com.tregouet.occam.data.problem_spaces.ICategorizationGoalState;

public interface ICompleteRepresentation extends 
	ICategorizationGoalState,
	IRepresentation, 
	Scored<LexicographicScore>, 
	Comparable<ICompleteRepresentation>{

}
