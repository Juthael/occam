package com.tregouet.occam.data.operators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public interface IConjunctiveOperator extends IOperator {
	
	StringBuilder prime = new StringBuilder();
	ListIterator<Character> charIte = new ArrayList<Character>(Arrays.asList(new Character[]{
					'Γ', 'Δ', 'Θ', 'Λ', 'Ξ', 'Π', 'Σ', 'Φ', 'Ψ', 'Ω'
					})).listIterator();
	
	static void initializeNameProvider() {
		while (charIte.hasPrevious())
			charIte.previous();
		prime.setLength(0);
	}
	
	static String provideName() {
		return getNextChar() + prime.toString();
	}
	
	private static char getNextChar() {
		if (!charIte.hasNext()) {
			while(charIte.hasPrevious())
				charIte.previous();
			prime.append("'");
		}
		return charIte.next();
	}
	
	boolean addOperator(IOperator operator);
	
	List<IOperator> getComponents();

}
