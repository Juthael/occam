package com.tregouet.occam.data.problem_space.states.productions.impl;

import java.util.ArrayList;
import java.util.Arrays;

import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;
import com.tregouet.occam.data.problem_space.states.transitions.impl.stack_default.vars.Nothing;
import com.tregouet.occam.data.problem_space.states.transitions.impl.stack_default.vars.This;

public class OmegaProd extends Production implements IProduction {
	
	public static final OmegaProd INSTANCE = new OmegaProd();
	
	private OmegaProd() {
		super(Nothing.INSTANCE, new Construct(new ArrayList<>(Arrays.asList(new ISymbol[] {This.INSTANCE}))));
	}

}
