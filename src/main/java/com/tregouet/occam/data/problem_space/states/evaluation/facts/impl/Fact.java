package com.tregouet.occam.data.problem_space.states.evaluation.facts.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.logical_structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.logical_structures.lambda_terms.impl.LambdaExpression;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.applications.IAbstractionApplication;
import com.tregouet.occam.data.problem_space.states.evaluation.facts.IFact;

public class Fact implements IFact {

	private final List<IAbstractionApplication> operatorList;

	public Fact(List<IAbstractionApplication> operatorList) {
		this.operatorList = operatorList;
	}

	@Override
	public ILambdaExpression asLambda() {
		return new LambdaExpression(operatorList);
	}

	@Override
	public List<IAbstractionApplication> asList() {
		return new ArrayList<>(operatorList);
	}

	@Override
	public IFact copy() {
		return new Fact(asList());
	}

	@Override
	public int size() {
		return operatorList.size();
	}

}
