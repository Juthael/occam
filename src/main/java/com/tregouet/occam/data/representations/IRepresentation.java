package com.tregouet.occam.data.representations;

import java.util.Set;

import com.tregouet.occam.data.problem_spaces.ICategorizationState;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedTree;

public interface IRepresentation extends ICategorizationState {
	
	InvertedTree<IConcept, IIsA> getTreeOfConcepts();
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object o);
	
	Set<ICompleteRepresentation> getRepresentationCompletions();

}
