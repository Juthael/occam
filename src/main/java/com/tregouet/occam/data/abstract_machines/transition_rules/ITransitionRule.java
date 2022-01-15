package com.tregouet.occam.data.abstract_machines.transition_rules;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.specific.IProductionAsEdge;

public interface ITransitionRule {
	
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
	
	IState getOutputState();
	
	IState getInputState();
	
	IProductionAsEdge getInputSymbol();
	
	AVariable getInputStackSymbol();
	
	AVariable getOutputStackSymbol();
	
	IInputConfiguration getInputConfiguration();
	
	IOutputInternConfiguration getOutputInternConfiguration();
	
	boolean isBlank();
	
	boolean isReframer();

}
