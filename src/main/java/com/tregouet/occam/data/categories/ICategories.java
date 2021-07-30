package com.tregouet.occam.data.categories;

import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.constructs.IContextObject;

public interface ICategories {
	
	boolean areA(List<ICategory> cats, ICategory cat);
	
	ICategory getAbsurdity();
	
	ICatTreeSupplier getCatTreeSupplier();
	
	ICatTreeWithConstrainedExtentStructureSupplier getCatTreeSupplier(IExtentStructureConstraint constraint);
	
	/**
	 * If param contains every object in the context, then return truism
	 * @param extent
	 * @return
	 */
	ICategory getCatWithExtent(Set<IContextObject> extent);
	
	ICategory getLeastCommonSuperordinate(Set<ICategory> categories);
	
	List<ICategory> getObjectCategories();
	
	ICategory getOntologicalCommitment();
	
	List<ICategory> getTopologicallySortedCategories();
	
	ICategory getTruism();
	
	ICategory getTruismAboutTruism();
	
	/**
	 * Not a reflexive relation
	 * @param cat1
	 * @param cat2
	 * @return
	 */
	boolean isA(ICategory cat1, ICategory cat2);
	
	boolean isADirectSubCategoryOf(ICategory cat1, ICategory cat2);

}
