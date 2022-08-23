package com.tregouet.occam.data.structures.representations.productions.impl;

import com.tregouet.occam.data.structures.representations.productions.IContextualizedProduction;

public class ContextualizedOmegaProd extends ContextualizedProd implements IContextualizedProduction {

	private static final long serialVersionUID = -1460009557249312841L;
	public static final ContextualizedOmegaProd INSTANCE = new ContextualizedOmegaProd();

	private ContextualizedOmegaProd() {
		super(null, null, OmegaProd.INSTANCE);
	}

}
