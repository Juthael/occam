package com.tregouet.occam.data.languages.generic;

import com.tregouet.occam.data.alphabets.ISymbol;

/**
 * <p>
 * A terminal symbol in an alphabet used by a context-free grammar.  
 * </p>
 *
 * @author Gael Tregouet
 */
public interface ITerminal extends ISymbol {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);	

}
