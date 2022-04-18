package com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.input_config.IInputConfiguration;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.output_config.IOutputInternConfiguration;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;

public interface ITransition<
	InputSymbol extends ISymbol,
	InputConfig extends IInputConfiguration<InputSymbol>,
	OutputConfig extends IOutputInternConfiguration
	> {

	StringBuilder prime = new StringBuilder();
	ListIterator<Character> charIte = populateCharList().listIterator();

	private static char getNextChar() {
		if (!charIte.hasNext()) {
			while(charIte.hasPrevious())
				charIte.previous();
			prime.append("'");
		}
		return charIte.next();
	}

	static void initializeNameProvider() {
		while (charIte.hasPrevious())
			charIte.previous();
		prime.setLength(0);
	}

	private static List<Character> populateCharList(){
		List<Character> authorizedCharASCII = new ArrayList<>();
		for (char curr = 'A' ; curr <= 'Z' ; curr++) {
			authorizedCharASCII.add(curr);
		}
		return authorizedCharASCII;
	}

	static String provideName() {
		return getNextChar() + prime.toString();
	}

	@Override
	boolean equals(Object o);

	InputConfig getInputConfiguration();

	String getName();

	OutputConfig getOutputInternConfiguration();

	@Override
	public int hashCode();

}
