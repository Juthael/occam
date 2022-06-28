package com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.abstr_app;

import java.util.List;

import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.problem_space.states.productions.IBasicProduction;

public interface IAbstractionApplication extends ISymbol {

	@Override
	boolean equals(Object o);

	/**
	 *
	 * @return arguments in the order of the index of their left term in the denotation's term
	 */
	List<IBasicProduction> getArguments();

	IBindings getBindings();

	@Override
	int hashCode();

	boolean isEpsilonOperator();

	boolean isIdentityOperator();

}
