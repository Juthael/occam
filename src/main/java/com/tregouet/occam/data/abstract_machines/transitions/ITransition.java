package com.tregouet.occam.data.abstract_machines.transitions;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.tregouet.occam.alg.calculators.costs.ICosted;
import com.tregouet.occam.data.abstract_machines.states.IState;

public interface ITransition extends ICosted {
	
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
	
	String getName();
	
	IState getNextState();
	
	IState getOperatingState();
	
	boolean isBlank();
	
	boolean isReframer();

}
