package com.tregouet.occam.data.structures.representations.productions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.structures.representations.productions.IBasicProduction;
import com.tregouet.occam.data.structures.representations.transitions.impl.stack_default.vars.Nothing;
import com.tregouet.occam.data.structures.representations.transitions.impl.stack_default.vars.This;

public class OmegaProd extends BasicProduction implements IBasicProduction {

	public static final OmegaProd INSTANCE = new OmegaProd();

	private OmegaProd() {
		super(Nothing.INSTANCE, new Construct(Arrays.asList(new ISymbol[] {This.INSTANCE})));
	}

}
