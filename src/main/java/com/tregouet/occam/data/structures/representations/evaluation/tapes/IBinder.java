package com.tregouet.occam.data.structures.representations.evaluation.tapes;

import com.tregouet.occam.data.structures.automata.tapes.IStack;
import com.tregouet.occam.data.structures.lambda_terms.IBindings;

public interface IBinder extends IStack<IBindings> {

	@Override
	IBinder copy();

	@Override
	boolean equals(Object o);

	@Override
	int hashCode();

}
