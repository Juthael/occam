package com.tregouet.occam.data.problem_space.states.evaluation.facts.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.logical_structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.logical_structures.lambda_terms.impl.BasicAbstractionApplication;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.IApplication;
import com.tregouet.occam.data.problem_space.states.evaluation.facts.IFact;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;

public class Fact implements IFact {

	private final List<IApplication> applicationList;

	public Fact(List<IApplication> applicationList) {
		this.applicationList = applicationList;
	}

	@Override
	public ILambdaExpression asLambda() {
		return new BasicAbstractionApplication(applicationList);
	}

	@Override
	public List<IApplication> asList() {
		return new ArrayList<>(applicationList);
	}

	@Override
	public IFact copy() {
		return new Fact(asList());
	}

	@Override
	public int size() {
		return applicationList.size();
	}

}
