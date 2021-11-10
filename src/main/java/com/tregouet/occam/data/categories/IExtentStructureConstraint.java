package com.tregouet.occam.data.categories;

import com.tregouet.occam.data.categories.impl.IsA;
import com.tregouet.tree_finder.data.Tree;

public interface IExtentStructureConstraint {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	boolean metBy(Tree<ICategory, IsA> catTree);

}
