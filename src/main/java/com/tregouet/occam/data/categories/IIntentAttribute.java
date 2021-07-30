package com.tregouet.occam.data.categories;

import com.tregouet.occam.data.constructs.IConstruct;

public interface IIntentAttribute extends IConstruct {
	
	public int hashCode();
	
	boolean equals(Object o);
	
	ICategory getCategory();

}
