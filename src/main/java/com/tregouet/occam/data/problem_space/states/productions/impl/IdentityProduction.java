package com.tregouet.occam.data.problem_space.states.productions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.problem_space.states.productions.IBasicProduction;
import com.tregouet.occam.data.problem_space.states.productions.Salience;

public class IdentityProduction extends BasicProduction implements IBasicProduction {

	public IdentityProduction(AVariable variable) {
		super(variable, new Construct(Arrays.asList(new ISymbol[] {variable})));
		setSalience(Salience.HIDDEN);
	}

	@Override
	public boolean isAlphaConversionProd() {
		return true;
	}

	@Override
	public boolean isIdentityProd() {
		return true;
	}

	@Override
	public boolean isEpsilon() {
		return false;
	}

}
