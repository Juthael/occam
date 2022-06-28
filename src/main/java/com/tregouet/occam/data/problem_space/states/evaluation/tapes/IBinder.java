package com.tregouet.occam.data.problem_space.states.evaluation.tapes;

import com.tregouet.occam.data.logical_structures.automata.tapes.IStack;
import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;

public interface IBinder extends IStack<IBindings> {

	@Override
	IBinder copy();

	@Override
	boolean equals(Object o);

	@Override
	int hashCode();

}
