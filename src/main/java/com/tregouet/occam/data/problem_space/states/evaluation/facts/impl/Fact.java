package com.tregouet.occam.data.problem_space.states.evaluation.facts.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.logical_structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.logical_structures.lambda_terms.impl.LambdaExpression;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.IComputation;
import com.tregouet.occam.data.problem_space.states.evaluation.facts.IFact;

public class Fact implements IFact {

	private final List<IComputation> computationList;

	public Fact(List<IComputation> computationList) {
		this.computationList = computationList;
	}

	@Override
	public ILambdaExpression asLambda() {
		return new LambdaExpression(computationList);
	}

	@Override
	public List<IComputation> asList() {
		return new ArrayList<>(computationList);
	}

	@Override
	public IFact copy() {
		return new Fact(asList());
	}

	@Override
	public int size() {
		return computationList.size();
	}

}
