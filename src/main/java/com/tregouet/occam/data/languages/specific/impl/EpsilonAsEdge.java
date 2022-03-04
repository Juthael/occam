package com.tregouet.occam.data.languages.specific.impl;

import com.tregouet.occam.data.concepts.IDenotation;
import com.tregouet.occam.data.languages.specific.IBasicProduction;
import com.tregouet.occam.data.languages.specific.IProductionAsEdge;

public class EpsilonAsEdge extends BasicProductionAsEdge implements IBasicProduction, IProductionAsEdge {

	private static final long serialVersionUID = -6093921194463496575L;
	
	public EpsilonAsEdge(IDenotation input, IDenotation output) {
		super(input, output, Epsilon.INSTANCE);
	}

}
