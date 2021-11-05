package com.tregouet.occam.data.operators;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.transition_function.IInfoMeter;
import com.tregouet.occam.transition_function.IState;

public interface IOperator {
	
	StringBuilder prime = new StringBuilder();
	ListIterator<Character> charIte = populateCharList().listIterator();
	
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
	
	private static List<Character> populateCharList(){
		List<Character> authorizedCharASCII = new ArrayList<Character>();
		for (char curr = 'A' ; curr <= 'Z' ; curr++) {
			authorizedCharASCII.add(curr);
		}
		return authorizedCharASCII;
	}
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	double getInformativity();
	
	String getName();
	
	IState getNextState();
	
	IState getOperatingState();
	
	boolean isBlank();
	
	IIntentAttribute operateOn(IIntentAttribute input);
	
	List<IProduction> operation();	
	
	List<ILambdaExpression> semantics();
	
	void setInformativity(IInfoMeter infometer);
	
	@Override
	String toString();	

}
