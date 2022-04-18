package com.tregouet.occam.data.logical_structures.languages.alphabets;

/**
 * <p>
 * A terminal symbol in an alphabet used by a context-free grammar.
 * </p>
 *
 * @author Gael Tregouet
 */
public interface ITerminal extends ISymbol {

	@Override
	boolean equals(Object o);

	@Override
	public int hashCode();

}
