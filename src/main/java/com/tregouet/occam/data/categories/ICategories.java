package com.tregouet.occam.data.categories;

import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.IContextObject;

public interface ICategories {
	
	List<ICategory> getObjectCategories();
	
	ICategory getTruism();
	
	ICategory getTruismAboutTruism();
	
	ICategory getOntologicalCommitment();
	
	List<ICategory> getTopologicallySortedCategories();
	
	ICatTreeSupplier getCatTreeSupplier();
	
	ICatTreeWithConstrainedExtentStructureSupplier getCatTreeSupplier(IExtentStructureConstraint constraint);
	
	boolean isA(ICategory cat1, ICategory cat2);
	
	boolean isADirectSubCategoryOf(ICategory cat1, ICategory cat2);

}
