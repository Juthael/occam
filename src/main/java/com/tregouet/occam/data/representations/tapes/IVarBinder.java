package com.tregouet.occam.data.representations.tapes;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.automata.tapes.IStackTape;

public interface IVarBinder extends IStackTape<AVariable> {
	
	@Override
	IVarBinder copy();

}
