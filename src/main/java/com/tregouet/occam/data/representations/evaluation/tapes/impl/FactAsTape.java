package com.tregouet.occam.data.representations.evaluation.tapes.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.representations.evaluation.tapes.IFactAsTape;

public class FactAsTape implements IFactAsTape {

	private final List<IContextualizedProduction> fact;
	private int index;
	
	public FactAsTape(List<IContextualizedProduction> fact, int index) {
		this.fact = fact;
		this.index = index;
	}
	
	public FactAsTape() {
		fact = new ArrayList<>();
		index = 0;
	}

	@Override
	public FactAsTape copy() {
		return new FactAsTape(new ArrayList<>(fact), index);
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
	public void initialize() {
		index = 0;
		
	}

	@Override
	public void print(IContextualizedProduction symbol) {
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
		FactAsTape other = (FactAsTape) obj;
		return Objects.equals(fact, other.fact);
	}

	@Override
	public IContextualizedProduction read() {
		if (!hasNext())
			return null;
		return next();
	}

}
