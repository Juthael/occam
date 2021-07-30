package com.tregouet.occam.data.categories.impl;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.impl.Construct;

public class IntentAttribute extends Construct implements IIntentAttribute {

	ICategory category = null;
	
	public IntentAttribute(IConstruct construct, ICategory category) {
		super(construct);
		this.category = category;
	}	

	public IntentAttribute(String[] progStrings) {
		super(progStrings);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		IntentAttribute other = (IntentAttribute) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		return true;
	}
	
	@Override
	public ICategory getCategory() {
		return category;
	}

	@Override
	public int hashCode() {
		//must not use Category.hashCode(), since Category.hashCode() uses this. 
		return super.hashCode();
	}

}
