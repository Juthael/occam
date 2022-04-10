package com.tregouet.occam.data.representations.evaluation.tapes;

import java.util.List;

import com.tregouet.occam.data.logical_structures.automata.tapes.IInputTape;
import com.tregouet.occam.data.representations.evaluation.facts.IFact;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;

public interface IFactTape extends IInputTape<IContextualizedProduction>, IFact {
	
	@Override
	IFactTape copy();
	
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
	
	IFact getFact();

}
