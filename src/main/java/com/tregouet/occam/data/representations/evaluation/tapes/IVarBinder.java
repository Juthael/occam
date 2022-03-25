package com.tregouet.occam.data.representations.evaluation.tapes;

import com.tregouet.occam.data.languages.alphabets.generic.AVariable;
import com.tregouet.occam.data.logical_structures.automata.tapes.IStack;

public interface IVarBinder extends IStack<AVariable> {
	
	@Override
	IVarBinder copy();
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object o);

}
