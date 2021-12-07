package com.tregouet.occam.data.concepts;

import com.tregouet.occam.data.constructs.IConstruct;

public interface IIntentAttribute extends IConstruct {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	IConcept getConcept();
	
	IIntentAttribute rebut(IComplementaryConcept complementaryConcept);

}
