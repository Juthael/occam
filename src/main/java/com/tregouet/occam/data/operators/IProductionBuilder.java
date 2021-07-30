package com.tregouet.occam.data.operators;

import java.util.List;

import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.operators.impl.Production;

public interface IProductionBuilder {
	
	List<Production> getProductions(ICategories categories);

}
