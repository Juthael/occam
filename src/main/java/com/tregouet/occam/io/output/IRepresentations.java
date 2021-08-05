package com.tregouet.occam.io.output;

import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IExtentStructureConstraint;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.transition_function.IAttributeConstraint;
import com.tregouet.occam.transition_function.ISimilarityConstraint;
import com.tregouet.occam.transition_function.ITFWithConstrainedExtentStructureSupplier;
import com.tregouet.occam.transition_function.ITFWithConstrainedPropertyStructureSupplier;
import com.tregouet.occam.transition_function.ITFWithConstrainedSimilarityStructureSupplier;
import com.tregouet.occam.transition_function.ITransitionFunctionSupplier;

public interface IRepresentations {

	ICategory getLeastCommonCategoryOf(Set<IContextObject> objects);
	
	ITFWithConstrainedPropertyStructureSupplier getRepresentationsSupplier(IAttributeConstraint constraint);
	
	ITransitionFunctionSupplier getRepresentationSupplier();
	
	ITFWithConstrainedExtentStructureSupplier getRepresentationSupplier(IExtentStructureConstraint constraint);
	
	ITFWithConstrainedSimilarityStructureSupplier getRepresentationSupplier(ISimilarityConstraint constraint);
	
	IRepresentations getSubContext(int[] objIndexes);
	
	IRepresentations getSubContext(List<List<Integer>> objIndexSets);
	
	IRepresentations getSubContext(Set<ICategory> categories);
	
}
