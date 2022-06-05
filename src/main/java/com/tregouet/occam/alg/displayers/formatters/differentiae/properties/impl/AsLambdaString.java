package com.tregouet.occam.alg.displayers.formatters.differentiae.properties.impl;

import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.PropertyLabeller;
import com.tregouet.occam.data.logical_structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.logical_structures.lambda_terms.impl.NaryConjunctiveAbstractionApplication;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.IProperty;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

public class AsLambdaString implements PropertyLabeller {

	public static final AsLambdaString INSTANCE = new AsLambdaString();

	private AsLambdaString() {
	}

	@Override
	public String apply(IProperty input) {
		ILambdaExpression exp = new NaryConjunctiveAbstractionApplication(input.getFunction());
		for (IContextualizedProduction application : input.getApplications())
			exp.abstractAndApplyAccordingTo(application.getUncontextualizedProduction());
		return exp.toString();
	}

}
