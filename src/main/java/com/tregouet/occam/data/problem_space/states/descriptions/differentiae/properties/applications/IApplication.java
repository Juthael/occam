package com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications;

import java.util.List;

import com.tregouet.occam.alg.setters.weighs.Weighed;
import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;

public interface IApplication extends ISymbol, Weighed {

	@Override
	boolean equals(Object o);

	/**
	 *
	 * @return arguments in the order of the index of their left term in the denotation's term
	 */
	List<IProduction> getArguments();

	IBindings getBindings();

	IDenotation getConstruct();

	IDenotation getValue();

	@Override
	int hashCode();

	boolean isBlank();

	boolean isEpsilon();

	void setWeight(double weight);

}
