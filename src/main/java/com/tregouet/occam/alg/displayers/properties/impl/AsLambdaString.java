package com.tregouet.occam.alg.displayers.properties.impl;

import com.tregouet.occam.alg.displayers.properties.PropertyDisplayer;
import com.tregouet.occam.data.logical_structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.logical_structures.lambda_terms.impl.NaryConjunctiveAbsractionApplication;
import com.tregouet.occam.data.representations.descriptions.properties.IProperty;
import com.tregouet.occam.data.representations.transitions.IApplication;

public class AsLambdaString implements PropertyDisplayer {
	
	public static final AsLambdaString INSTANCE = new AsLambdaString();
	
	private AsLambdaString() {
	}

	@Override
	public String apply(IProperty input) {
		ILambdaExpression exp = new NaryConjunctiveAbsractionApplication(input.getFunction());
		for (IApplication application : input.getApplications())
			exp.abstractAndApplyAccordingTo(application.getInputConfiguration().getInputSymbol());
		return exp.toString();
	}

}
