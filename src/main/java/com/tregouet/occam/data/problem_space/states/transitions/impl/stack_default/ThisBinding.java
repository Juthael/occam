package com.tregouet.occam.data.problem_space.states.transitions.impl.stack_default;

import java.util.ArrayList;
import java.util.Arrays;

import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.logical_structures.lambda_terms.impl.Bindings;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.transitions.impl.stack_default.vars.This;

public class ThisBinding extends Bindings implements IBindings {

	public static final ThisBinding INSTANCE = new ThisBinding();

	private ThisBinding() {
		super(new ArrayList<>(Arrays.asList(new AVariable[] {This.INSTANCE})));
	}

}
