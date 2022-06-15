package com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.applications;

import java.util.List;

import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.problem_space.states.productions.IBasicProduction;

public interface IAbstractionApplication extends ISymbol {

	IBindings getBindings();

	/**
	 *
	 * @return arguments in the order of the index of their left term in the denotation's term
	 */
	List<IBasicProduction> getArguments();

	@Override
	boolean equals(Object o);

	@Override
	int hashCode();

	boolean isIdentityOperator();

	boolean isEpsilonOperator();

}
