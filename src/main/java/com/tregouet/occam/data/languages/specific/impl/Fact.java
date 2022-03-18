package com.tregouet.occam.data.languages.specific.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.languages.specific.IFact;

public class Fact implements IFact {

	private final List<IContextualizedProduction> fact;
	private int index;
	
	public Fact(List<IContextualizedProduction> fact, int index) {
		this.fact = fact;
		this.index = index;
	}
	
	public Fact() {
		fact = new ArrayList<>();
		index = 0;
	}

	@Override
	public Fact copy() {
		return new Fact(new ArrayList<>(fact), index);
	}

	@Override
	public boolean hasNext() {
		return index < fact.size() - 1;
	}

	@Override
	public List<IContextualizedProduction> getListOfSymbols() {
		return new ArrayList<>(fact);
	}

	@Override
	public IContextualizedProduction next() {
		return fact.get(index++);
	}

	@Override
	public void initializeSymbolIterator() {
		index = 0;
		
	}

	@Override
	public void appendSymbol(IContextualizedProduction symbol) {
		fact.add(symbol);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fact);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fact other = (Fact) obj;
		return Objects.equals(fact, other.fact);
	}

}
