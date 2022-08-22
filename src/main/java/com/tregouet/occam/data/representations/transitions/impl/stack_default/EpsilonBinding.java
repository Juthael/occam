package com.tregouet.occam.data.representations.transitions.impl.stack_default;

import java.util.ArrayList;
import java.util.Arrays;

import com.tregouet.occam.data.representations.transitions.impl.stack_default.vars.Epsilon;
import com.tregouet.occam.data.structures.lambda_terms.IBindings;
import com.tregouet.occam.data.structures.lambda_terms.impl.Bindings;
import com.tregouet.occam.data.structures.languages.alphabets.AVariable;

public class EpsilonBinding extends Bindings implements IBindings {

	public static final EpsilonBinding INSTANCE = new EpsilonBinding();

	private EpsilonBinding() {
		super(new ArrayList<>(Arrays.asList(new AVariable[] {Epsilon.INSTANCE})));
	}

}
