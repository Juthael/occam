package com.tregouet.occam.data.problem_space.states.productions;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;

public interface IProduction {

	boolean derives(AVariable var);

	@Override
	boolean equals(Object o);

	Salience getSalience();

	IConstruct getValue();

	AVariable getVariable();

	@Override
	int hashCode();

	/**
	 *
	 * @return true if the right term only contains a variable
	 */
	boolean isAlphaConversion();

	/**
	 *
	 * @return true if left term and right term are the same
	 */
	boolean isIdentityProd();

	// Denotes an empty string of symbols. As symbols here are productions,
	// also denotes that no variable is derived.
	boolean isEpsilon();

	void setSalience(Salience salience);

	@Override
	String toString();

}
