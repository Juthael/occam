package com.tregouet.occam.alg.displayers.graph_labellers.differentiae.properties.impl;

import com.tregouet.occam.alg.displayers.graph_labellers.differentiae.properties.PropertyLabeller;
import com.tregouet.occam.data.logical_structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.logical_structures.lambda_terms.impl.NaryConjunctiveAbstractionApplication;
import com.tregouet.occam.data.representations.descriptions.properties.IProperty;
import com.tregouet.occam.data.representations.transitions.IApplication;

public class AsLambdaString implements PropertyLabeller {
	
	public static final AsLambdaString INSTANCE = new AsLambdaString();
	
	private AsLambdaString() {
	}

	@Override
	public String apply(IProperty input) {
		ILambdaExpression exp = new NaryConjunctiveAbstractionApplication(input.getFunction());
		for (IApplication application : input.getApplications())
			exp.abstractAndApplyAccordingTo(application.getInputConfiguration().getInputSymbol());
		return exp.toString();
	}

}
