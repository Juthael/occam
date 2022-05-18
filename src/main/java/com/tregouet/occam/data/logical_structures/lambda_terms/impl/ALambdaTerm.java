package com.tregouet.occam.data.logical_structures.lambda_terms.impl;

import com.tregouet.occam.data.logical_structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.problem_space.states.transitions.productions.IProduction;

public abstract class ALambdaTerm implements ILambdaExpression {

	protected IConstruct term = null;

	public ALambdaTerm(IConstruct term) {
		this.term = term;
	}

	@Override
	abstract public boolean abstractAndApplyAccordingTo(IProduction production);

	@Override
	abstract public boolean isAnApplication();

}
