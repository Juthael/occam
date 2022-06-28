package com.tregouet.occam.data.logical_structures.languages.alphabets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;

/**
 * <p>
 * A formal variable, used as a placeholder for some unspecified part of an
 * abstract construct ({@link IConstruct}). Variables can be assigned a value by
 * domain-specific operators ({@link IDSOperator}), which leads to the
 * generation of a new (and less abstract) construct.
 * </p>
 *
 * <p>
 * A given variable should never be found in distinct constructs, nor in similar
 * constructs belonging to the intent of distinct categories ({@link IConcept}).
 * This way, a variable can be referred to as a <i>dimension</i> : its biding by
 * a domain-specific operator yields a function that expresses what is common to
 * all constructs obtained by this variable's assignment of a <i>value</i>, and
 * the set of accepted values are the attributes accessible in this dimension.
 * </p>
 *
 * @author Gael Tregouet
 *
 */
public abstract class AVariable implements ISymbol {

	public static boolean DEFERRED_NAMING = true;
	private static int iterationsOverAlphabet = 0;
	private static List<Character> charList = populateCharList();
	private static Iterator<Character> charIte = charList.iterator();

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

	protected String provideName() {
		StringBuffer sB = new StringBuffer();
		sB.append(getNextChar());
		for (int i = 0; i < iterationsOverAlphabet; i++) {
			sB.append("'");
		}
		return sB.toString();
	}

	private char getNextChar() {
		if (!charIte.hasNext()) {
			charIte = populateCharList().iterator();
			iterationsOverAlphabet++;
		}
		return charIte.next();
	}

	public static void resetVarNaming() {
		iterationsOverAlphabet = 0;
		charIte = charList.iterator();
	}

	private static List<Character> populateCharList() {
		List<Character> authorizedCharASCII = new ArrayList<>();
		for (char curr = 'a'; curr <= 'z'; curr++) {
			authorizedCharASCII.add(curr);
		}
		return authorizedCharASCII;
	}

}
