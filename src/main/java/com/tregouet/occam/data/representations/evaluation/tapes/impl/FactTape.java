package com.tregouet.occam.data.representations.evaluation.tapes.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.logical_structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.representations.evaluation.facts.IFact;
import com.tregouet.occam.data.representations.evaluation.facts.impl.Fact;
import com.tregouet.occam.data.representations.evaluation.tapes.IFactTape;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;

public class FactTape implements IFactTape {

	private final List<IContextualizedProduction> fact;
	private int index;
	
	public FactTape(List<IContextualizedProduction> fact, int index) {
		this.fact = fact;
		this.index = index;
	}
	
	public FactTape(IFact fact) {
		this.fact = fact.asList();
		this.index = 0;
	}
	
	public FactTape() {
		fact = new ArrayList<>();
		index = 0;
	}

	@Override
	public FactTape copy() {
		return new FactTape(new ArrayList<>(fact), index);
	}

	@Override
	public boolean hasNext() {
		return index < fact.size() - 1;
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
		FactTape other = (FactTape) obj;
		return Objects.equals(fact, other.fact);
	}

	@Override
	public IContextualizedProduction read() {
		if (!hasNext())
			return null;
		return fact.get(index++);
	}

	@Override
	public ILambdaExpression asLambda() {
		return new Fact(fact).asLambda();
	}

	@Override
	public List<IContextualizedProduction> asList() {
		return fact;
	}

	@Override
	public IFact getFact() {
		return new Fact(fact);
	}

}
