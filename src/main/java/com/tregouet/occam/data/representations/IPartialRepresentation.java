package com.tregouet.occam.data.representations;

import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;
import com.tregouet.tree_finder.data.InvertedTree;

public interface IPartialRepresentation extends IRepresentation {
	
	void setClassification(InvertedTree<IConcept, IIsA> classification);
	
	void setUpFactEvaluator(IRepresentationTransitionFunction transFunc);
	
	void setDescription(IDescription description);

}
