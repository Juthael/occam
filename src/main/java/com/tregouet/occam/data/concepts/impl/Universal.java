package com.tregouet.occam.data.concepts.impl;

import java.util.List;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IDifferentiae;
import com.tregouet.occam.data.concepts.IUniversal;
import com.tregouet.occam.data.preconcepts.IPreconcept;

public class Universal extends Concept implements IUniversal {

	private List<IConcept> species;
	private IDifferentiae[] speciesDiff;
	
	public Universal(IPreconcept preconcept) {
		super(preconcept);
		// TODO Auto-generated constructor stub
	}

}
