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
	public ICategory getCategory() {
		return category;
	}

}
