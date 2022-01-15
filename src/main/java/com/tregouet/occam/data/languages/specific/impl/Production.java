package com.tregouet.occam.data.languages.specific.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.languages.ISymbol;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.data.languages.specific.IProduction;

public abstract class Production implements IProduction {
	
	protected AVariable variable;

	@Override
	public AVariable getVariable() {
		return variable;
	}

}
