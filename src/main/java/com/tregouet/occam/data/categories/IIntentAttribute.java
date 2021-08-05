package com.tregouet.occam.data.categories;

import com.tregouet.occam.data.constructs.IConstruct;

public interface IIntentAttribute extends IConstruct {
	
	@Override
	public int hashCode();
	
	@Override
	boolean equals(Object o);
	
	ICategory getCategory();

}
