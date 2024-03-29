package com.tregouet.occam.data.structures.lambda_terms;

import java.util.List;

import com.tregouet.occam.data.structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.structures.languages.alphabets.ISymbol;

public interface IBindings extends ISymbol {

	@Override
	boolean equals(Object o);

	List<AVariable> getBoundVariables();

	@Override
	int hashCode();

	@Override
	String toString();

}
