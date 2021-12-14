package com.tregouet.occam.data.concepts;

import com.tregouet.tree_finder.data.Tree;

public interface IExtentStructureConstraint {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	boolean metBy(Tree<IConcept, IIsA> catTree);

}
