package com.tregouet.occam.data.denotations;

import com.tregouet.tree_finder.data.Tree;

public interface IExtentStructureConstraint {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	boolean metBy(Tree<IDenotationSet, IIsA> catTree);

}
