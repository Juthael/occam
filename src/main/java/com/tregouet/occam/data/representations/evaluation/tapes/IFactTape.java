package com.tregouet.occam.data.representations.evaluation.tapes;

import java.util.List;

import com.tregouet.occam.data.representations.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;
import com.tregouet.occam.data.representations.evaluation.facts.IFact;
import com.tregouet.occam.data.representations.productions.IProduction;
import com.tregouet.occam.data.structures.automata.tapes.IInputTape;

public interface IFactTape extends IInputTape<IAbstractionApplication>, IFact {

	@Override
	public int hashCode();

	@Override
	IFactTape copy();

	@Override
	boolean equals(Object o);

	IFact getFact();

	public static boolean isAWellFormedFact(List<IProduction> word) {
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

}
