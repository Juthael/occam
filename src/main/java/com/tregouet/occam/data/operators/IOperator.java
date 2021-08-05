package com.tregouet.occam.data.operators;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.transition_function.IState;

public interface IOperator {
	
	StringBuilder prime = new StringBuilder();
	ListIterator<Character> charIte = populateCharList().listIterator();
	
	public int hashCode();
	
	boolean equals(Object o);
	
	double getCost();
	
	IState getNextState();
	
	IState getOperatingState();
	
	IIntentAttribute operateOn(IIntentAttribute input);
	
	List<IProduction> operation();
	
	List<ILambdaExpression> semantics();
	
	String getName();
	
	String toString();
	
	boolean isBlank();
	
	static String provideName() {
		return getNextChar() + prime.toString();
	}	
	
	static void initializeNameProvider() {
		while (charIte.hasPrevious())
			charIte.previous();
		prime.setLength(0);
	}
	
	private static List<Character> populateCharList(){
		List<Character> authorizedCharASCII = new ArrayList<Character>();
		for (char curr = 'A' ; curr <= 'Z' ; curr++) {
			authorizedCharASCII.add(curr);
		}
		return authorizedCharASCII;
	}
	
	private static char getNextChar() {
		if (!charIte.hasNext()) {
			while(charIte.hasPrevious())
				charIte.previous();
			prime.append("'");
		}
		return charIte.next();
	}	

}
