package com.tregouet.occam.data.concepts;

import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.tree_finder.data.Tree;

public interface IClassification {
	
	Tree<IConcept, IsA> getClassificationTree();
	
	double getCostOf(IsA relation);
	
}
