package com.tregouet.occam.data.categories;

import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.constructs.IContextObject;

public interface ICategories {
	
	List<ICategory> getObjectCategories();
	
	ICategory getAbsurdity();
	
	ICategory getTruism();
	
	ICategory getTruismAboutTruism();
	
	ICategory getOntologicalCommitment();
	
	List<ICategory> getTopologicallySortedCategories();
	
	ICatTreeSupplier getCatTreeSupplier();
	
	/**
	 * If param contains every object in the context, then return truism
	 * @param extent
	 * @return
	 */
	ICategory getCatWithExtent(Set<IContextObject> extent);
	
	ICategory getLeastCommonSuperordinate(Set<ICategory> categories);
	
	ICatTreeWithConstrainedExtentStructureSupplier getCatTreeSupplier(IExtentStructureConstraint constraint);
	
	/**
	 * Not a reflexive relation
	 * @param cat1
	 * @param cat2
	 * @return
	 */
	boolean isA(ICategory cat1, ICategory cat2);
	
	boolean areA(List<ICategory> cats, ICategory cat);
	
	boolean isADirectSubCategoryOf(ICategory cat1, ICategory cat2);

}
