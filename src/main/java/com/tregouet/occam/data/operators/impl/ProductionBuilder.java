package com.tregouet.occam.data.operators.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.IProductionBuilder;
import com.tregouet.occam.data.operators.impl.util.ProductionGenerator;

public class ProductionBuilder implements IProductionBuilder {

	private final List<ICategory> topologicalOrderOnCats;
	private final List<IProduction> productions = new ArrayList<>();
	
	public ProductionBuilder(ICategories categories) {
		topologicalOrderOnCats = categories.getTopologicalSorting();
		//topologicalOrderOnCats[0] is the 'absurdity'. Productions from its attributes are meaningless.
		for (int i = 1 ; i < topologicalOrderOnCats.size() - 1 ; i++) {
			for (int j = i+1 ; j < topologicalOrderOnCats.size() ; j++) {
				for (IIntentAttribute iCatAtt : topologicalOrderOnCats.get(i).getIntent()) {
					for (IIntentAttribute jCatAtt : topologicalOrderOnCats.get(j).getIntent()) {
						List<IProduction> ijAttProds = 
								new ProductionGenerator(categories, iCatAtt, jCatAtt).getProduction();
						if (ijAttProds != null)
							productions.addAll(ijAttProds);
					}
				}
			}
		}
	}

	@Override
	public List<IProduction> getProductions() {
		return productions;
	}

}
