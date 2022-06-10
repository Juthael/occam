package com.tregouet.occam.data.problem_space.states.evaluation.tapes.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.tregouet.occam.data.logical_structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.IApplication;
import com.tregouet.occam.data.problem_space.states.evaluation.facts.IFact;
import com.tregouet.occam.data.problem_space.states.evaluation.facts.impl.Fact;
import com.tregouet.occam.data.problem_space.states.evaluation.tapes.IFactTape;

public class FactTape implements IFactTape {

	private final List<IApplication> fact;
	private int index;

	public FactTape() {
		fact = new ArrayList<>();
		index = 0;
	}

	public FactTape(IFact fact) {
		this.fact = fact.asList();
		this.index = 0;
	}

	public FactTape(List<IApplication> fact, int index) {
		this.fact = fact;
		this.index = index;
	}

	@Override
	public ILambdaExpression asLambda() {
		return new Fact(fact).asLambda();
	}

	@Override
	public List<IApplication> asList() {
		return fact;
	}

	@Override
	public FactTape copy() {
		return new FactTape(new ArrayList<>(fact), index);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		FactTape other = (FactTape) obj;
		return Objects.equals(fact, other.fact);
	}

	@Override
	public IFact getFact() {
		return new Fact(fact);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fact);
	}

	@Override
	public boolean hasNext() {
		return index < fact.size() - 1;
	}

	@Override
	public void print(IApplication symbol) {
		fact.add(symbol);
	}

	@Override
	public IApplication read() {
		if (!hasNext())
			return null;
		return fact.get(index++);
	}

	@Override
	public int size() {
		return fact.size();
	}

}
