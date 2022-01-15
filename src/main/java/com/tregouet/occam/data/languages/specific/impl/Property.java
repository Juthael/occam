package com.tregouet.occam.data.languages.specific.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.specific.IEdgeProduction;
import com.tregouet.occam.data.languages.specific.IProperty;

public class Property implements IProperty {

	private List<IEdgeProduction> edgeProductions;
	private Iterator<IEdgeProduction> prodIte = null;
	
	public Property() {
		edgeProductions = new ArrayList<>();
	}
	
	public Property(List<IEdgeProduction> edgeProductions) {
		this.edgeProductions = edgeProductions;
	}
	
	@Override
	public List<IEdgeProduction> getListOfSymbols() {
		return edgeProductions;
	}

	@Override
	public boolean hasNext() {
		if (prodIte == null)
			initializeSymbolIterator();
		return prodIte.hasNext();
	}

	@Override
	public IEdgeProduction next() {
		return prodIte.next();
	}

	@Override
	public void initializeSymbolIterator() {
		prodIte = edgeProductions.iterator();
	}

	@Override
	public boolean appendSymbol(IEdgeProduction symbol) {
		Set<AVariable> freeVariables = new HashSet<>();
		for (IEdgeProduction edgeProduction : edgeProductions) {
			freeVariables.addAll(edgeProduction.getVariables());
		}
	}

}
