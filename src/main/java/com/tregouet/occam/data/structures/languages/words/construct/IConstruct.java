package com.tregouet.occam.data.structures.languages.words.construct;

import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.structures.languages.alphabets.ITerminal;
import com.tregouet.occam.data.structures.languages.words.IWord;
import com.tregouet.occam.data.structures.partial_order.PartiallyComparable;

public interface IConstruct extends IWord<ISymbol>, ILambdaExpression, PartiallyComparable<IConstruct> {

	public static final String EMPTY_CONSTRUCT_SYMBOL = "ε";

	@Override
	public IConstruct copy();

	@Override
	public int hashCode();

	@Override
	boolean equals(Object o);

	Iterator<ISymbol> getIteratorOverSymbols();

	List<ITerminal> getListOfTerminals();

	int getNbOfTerminals();

	/**
	 *
	 * @return variables in their index order
	 */
	List<AVariable> getVariables();

	boolean isAbstract();

	boolean isAlphaConvertibleWith(IConstruct other);

	/**
	 *
	 * @param constraint a construct that is not asbtract
	 * @return
	 */
	boolean meets(IConstruct constraint);

	void nameVariables();

	List<String> toListOfStringsWithPlaceholders();

	@Override
	String toString();

}
