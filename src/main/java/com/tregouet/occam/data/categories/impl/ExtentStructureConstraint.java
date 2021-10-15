package com.tregouet.occam.data.categories.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IExtentStructureConstraint;
import com.tregouet.tree_finder.data.ClassificationTree;

public class ExtentStructureConstraint implements IExtentStructureConstraint {

	private Map<List<Integer>, List<Integer>> extentIdxToSuperordinateExtentIdx = new HashMap<>();
	
	/**
	 * Parameter must be in the form (((ik)(jlm))(k)), such as <i>i, j, k, l, m</i> are indexes in a list 
	 * of objects. Constraint will be met if :  
	 * <ul>
	 * 	<li> The tree contains categories whose exact extent is respectively {i, k}, {j, l, m}, {i, k, j, l, m}, 
	 * {k}, {i, j, k, l, m} // A REVOIR 
	 * 	<li> 
	 * @param constraint
	 */
	public ExtentStructureConstraint(String constraint) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean metBy(ClassificationTree<ICategory, DefaultEdge> catTree) {
		// TODO Auto-generated method stub
		return false;
	}

}
