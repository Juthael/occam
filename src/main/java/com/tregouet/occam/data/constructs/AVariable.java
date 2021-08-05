package com.tregouet.occam.data.constructs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.categories.ICategory;

/**
 * <p>
 * A formal variable, used as a placeholder for some unspecified part of an abstract construct ({@link IConstruct}). 
 * Variables can be assigned a value by domain-specific operators ({@link IDSOperator}), which leads to the generation of 
 * a new (and less abstract) construct. 
 * </p> 
 * 
 * <p>
 * A given variable should never be found in distinct constructs, nor in similar constructs belonging to the intent of 
 * distinct categories ({@link ICategory}). This way, a variable can be referred to as a <i>dimension</i> :  
 * its biding by a domain-specific operator yields a function that expresses what is common to all constructs obtained by 
 * this variable's assignment of a <i>value</i>, and the set of accepted values are the attributes accessible in this dimension.
 * </p>
 * 
 * @author Gael Tregouet
 *
 */
public abstract class AVariable implements ISymbol {
	
	public static boolean DEFERRED_NAMING = true;
	
	private static int iterationsOverAlphabet = 0;
	private static Iterator<Character> charIte = populateCharList().iterator();
	
	private static List<Character> populateCharList(){
		List<Character> authorizedCharASCII = new ArrayList<Character>();
		for (char curr = 'a' ; curr <= 'z' ; curr++) {
			authorizedCharASCII.add(curr);
		}
		for (char curr = 945 ; curr <= 965 ; curr++) {
			authorizedCharASCII.add(curr);
		}
		return authorizedCharASCII;
	}
	
	@Override
	public abstract boolean equals(Object o);
	
	/**
	 * 
	 * @return this variable's name
	 */
	abstract public String getName();	
	
	@Override
	public abstract int hashCode();
	
	/**
	 * Assigns a new arbitrary name to the variable.
	 */
	abstract public void setName();
	
	private char getNextChar() {
		if (!charIte.hasNext()) {
			charIte = populateCharList().iterator();
			iterationsOverAlphabet++;
		}
		return charIte.next();
	}
	
	protected String provideName() {
		StringBuffer sB = new StringBuffer();
		sB.append(getNextChar());
		for (int i = 0 ; i < iterationsOverAlphabet ; i++) {
			sB.append("'");
		}
		return sB.toString();
	}

}
