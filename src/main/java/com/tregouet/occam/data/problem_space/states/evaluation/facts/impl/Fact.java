package com.tregouet.occam.data.problem_space.states.evaluation.facts.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.logical_structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.logical_structures.lambda_terms.impl.BasicAbstractionApplication;
import com.tregouet.occam.data.problem_space.states.evaluation.facts.IFact;
import com.tregouet.occam.data.problem_space.states.transitions.productions.IContextualizedProduction;

public class Fact implements IFact {

	private final List<IContextualizedProduction> productionList;

	public Fact(List<IContextualizedProduction> productionList) {
		this.productionList = productionList;
	}

	@Override
	public ILambdaExpression asLambda() {
		return new BasicAbstractionApplication(productionList);
	}

	@Override
	public List<IContextualizedProduction> asList() {
		return new ArrayList<>(productionList);
	}

	@Override
	public IFact copy() {
		return new Fact(asList());
	}

}
