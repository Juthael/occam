package com.tregouet.occam.alg.displayers.formatters.differentiae.properties.impl;

import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.PropertyLabeller;
import com.tregouet.occam.data.logical_structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.logical_structures.lambda_terms.impl.NaryConjunctiveAbstractionApplication;
import com.tregouet.occam.data.representations.descriptions.properties.IProperty;
import com.tregouet.occam.data.representations.transitions.IApplication;

public class AsLambdaShorterString implements PropertyLabeller {

	public static final AsLambdaShorterString INSTANCE = new AsLambdaShorterString();

	private AsLambdaShorterString() {
	}

	@Override
	public String apply(IProperty input) {
		ILambdaExpression exp = new NaryConjunctiveAbstractionApplication(input.getFunction());
		for (IApplication application : input.getApplications())
			exp.abstractAndApplyAccordingTo(application.getInputConfiguration().getInputSymbol());
		return exp.toShorterString();
	}

}
