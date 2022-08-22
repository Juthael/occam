package com.tregouet.occam.data.representations.descriptions.differentiae.properties.computations.abstr_app;

import java.util.List;

import com.tregouet.occam.data.representations.productions.IBasicProduction;
import com.tregouet.occam.data.structures.lambda_terms.IBindings;
import com.tregouet.occam.data.structures.languages.alphabets.ISymbol;

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
