package com.tregouet.occam.data.representations;

import com.tregouet.occam.alg.scorers.Scored;
import com.tregouet.occam.data.logical_structures.scores.impl.LexicographicScore;
import com.tregouet.occam.data.problem_spaces.ICategorizationGoalState;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedTree;

public interface IRepresentation extends 
	Scored<LexicographicScore>, 
	Comparable<IRepresentation>, 
	ICategorizationGoalState {
	
	InvertedTree<IConcept, IIsA> getTreeOfConcepts();
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object o);

}
