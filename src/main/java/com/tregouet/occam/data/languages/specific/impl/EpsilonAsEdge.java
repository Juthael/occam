package com.tregouet.occam.data.languages.specific.impl;

import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.languages.specific.IBasicProductionAsEdge;

public class EpsilonAsEdge extends BasicProductionAsEdge implements IBasicProductionAsEdge {

	private static final long serialVersionUID = -6093921194463496575L;
	
	public EpsilonAsEdge(IDenotation input, IDenotation output) {
		super(input, output, Epsilon.INSTANCE);
		// TODO Auto-generated constructor stub
	}

}
