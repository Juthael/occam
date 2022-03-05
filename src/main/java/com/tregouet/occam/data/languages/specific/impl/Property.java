package com.tregouet.occam.data.languages.specific.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.specific.IProduction;
import com.tregouet.occam.data.languages.specific.IProperty;

public class Property implements IProperty {

	private List<IProduction> productions;
	private Iterator<IProduction> prodIte = null;
	
	public Property() {
		productions = new ArrayList<>();
	}
	
	public Property(List<IProduction> edgeProductions) {
		this.productions = edgeProductions;
	}
	
	@Override
	public List<IProduction> getListOfSymbols() {
		return productions;
	}

	@Override
	public boolean hasNext() {
		if (prodIte == null)
			initializeSymbolIterator();
		return prodIte.hasNext();
	}

	@Override
	public IProduction next() {
		return prodIte.next();
	}

	@Override
	public void initializeSymbolIterator() {
		prodIte = productions.iterator();
	}

	@Override
	public boolean appendSymbol(IProduction symbol) {
		Set<AVariable> freeVariables = new HashSet<>();
		for (IProduction production : productions) {
			freeVariables.addAll(production.getValue().getVariables());
			freeVariables.remove(production.getVariable());
		}
		if (freeVariables.contains(symbol.getVariable())) {
			productions.add(symbol);
			return true;
		}
		return false;
	}

}