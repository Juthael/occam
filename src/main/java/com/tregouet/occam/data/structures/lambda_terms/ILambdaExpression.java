package com.tregouet.occam.data.structures.lambda_terms;

import java.util.Set;

import com.tregouet.occam.data.structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;

public interface ILambdaExpression {

	ILambdaExpression abstractAndApply(IAbstractionApplication abstrApp, boolean safeMode);

	@Override
	boolean equals(Object o);

	Set<AVariable> getFreeVariables();

	@Override
	int hashCode();

	boolean isAbstractionApplication();

	@Override
	String toString();

}
