package com.tregouet.occam.data.problem_space.states.evaluation.tapes;

import com.tregouet.occam.data.logical_structures.automata.tapes.IStack;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;

public interface IVarBinder extends IStack<AVariable> {

	@Override
	IVarBinder copy();

	@Override
	boolean equals(Object o);

	@Override
	int hashCode();

}
