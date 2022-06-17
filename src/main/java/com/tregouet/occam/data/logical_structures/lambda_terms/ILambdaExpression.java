package com.tregouet.occam.data.logical_structures.lambda_terms;

import java.util.Set;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;

public interface ILambdaExpression {

	ILambdaExpression abstractAndApply(IAbstractionApplication abstrApp, boolean safeMode);
	
	Set<AVariable> getFreeVariables();

	@Override
	boolean equals(Object o);

	@Override
	int hashCode();

	boolean isAbstractionApplication();

	@Override
	String toString();

}
