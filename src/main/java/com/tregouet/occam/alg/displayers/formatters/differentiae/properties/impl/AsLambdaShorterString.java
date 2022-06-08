package com.tregouet.occam.alg.displayers.formatters.differentiae.properties.impl;

import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.PropertyLabeller;
import com.tregouet.occam.data.logical_structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.logical_structures.lambda_terms.impl.NaryConjunctiveAbstractionApplication;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

public class AsLambdaShorterString implements PropertyLabeller {

	public static final AsLambdaShorterString INSTANCE = new AsLambdaShorterString();

	private AsLambdaShorterString() {
	}

	@Override
	public String apply(IProperty input) {
		ILambdaExpression exp = new NaryConjunctiveAbstractionApplication(input.function());
		for (IContextualizedProduction production : input.getProductions())
			exp.abstractAndApplyAccordingTo(production.getUncontextualizedProduction());
		return exp.toShorterString();
	}

}
