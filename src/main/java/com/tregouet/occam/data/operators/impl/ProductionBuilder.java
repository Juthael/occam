package com.tregouet.occam.data.operators.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.categories.impl.IntentAttribute;
import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.ISymbol;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.IProductionBuilder;

public class ProductionBuilder implements IProductionBuilder {

	private final ICategories categories;
	private final List<ICategory> topologicalOrderOnCats;
	private final List<IProduction> productions;
	
	public ProductionBuilder(ICategories categories) {
		this.categories = categories;
		topologicalOrderOnCats = categories.getTopologicallySortedCategories();
		productions = buildProductions();
	}

	@Override
	public List<IProduction> getProductions() {
		return productions;
	}
	
	private IProduction buildProduction(IIntentAttribute att1, IIntentAttribute att2) {
		if (categories.isA(att1.getCategory(), att2.getCategory())) {
			if (att1.getListOfSymbols().equals(att2.getListOfSymbols()))
				return new BlankProduction(att1, att2);
			if (!att1.getListOfTerminals().containsAll(att2.getListOfTerminals()))
				return null;
			Iterator<ISymbol> att1Ite = att1.getListOfSymbols().iterator();
			List<ISymbol> att2Symbols = att2.getListOfSymbols();
			int att2Idx = 0;
			List<ISymbol> buffer = new ArrayList<>();
			while (att1Ite.hasNext() && att2Idx < att2Symbols.size()) {
				ISymbol nextSymbol = att1Ite.next();
				if (nextSymbol.equals(att2Symbols.get(att2Idx))) {
					
				}
			}
		}
		return null;
	}

}
