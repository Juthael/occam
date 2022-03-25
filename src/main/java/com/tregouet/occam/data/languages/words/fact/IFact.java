package com.tregouet.occam.data.languages.words.fact;

import java.util.List;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.logical_structures.automata.tapes.IInputTape;

public interface IFact extends IInputTape<IContextualizedProduction> {
	
	@Override
	IFact copy();
	
	public static boolean isAWellFormedFact(List<IContextualizedProduction> word) {
		if (word.isEmpty())
			return false;
		if (word.size() == 1)
			return true;
		int i = 0;
		int j = 1;
		while (j < word.size()) {
			if (!word.get(i).getValue().getVariables().contains(word.get(j).getVariable()))
				return false;
			i++;
			j++;
		}
		return true;
	}	
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);	

}
