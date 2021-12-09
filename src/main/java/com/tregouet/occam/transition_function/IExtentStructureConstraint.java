package com.tregouet.occam.transition_function;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.tree_finder.data.Tree;

public interface IExtentStructureConstraint {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	boolean metBy(Tree<IConcept, IsA> catTree);

}
