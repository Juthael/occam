package com.tregouet.occam.data.representations.productions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.representations.productions.IBasicProduction;
import com.tregouet.occam.data.representations.productions.Salience;
import com.tregouet.occam.data.structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.structures.languages.words.construct.impl.Construct;

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
	public boolean isEpsilon() {
		return false;
	}

	@Override
	public boolean isIdentityProd() {
		return true;
	}

}
