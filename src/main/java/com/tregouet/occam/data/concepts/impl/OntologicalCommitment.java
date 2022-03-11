package com.tregouet.occam.data.concepts.impl;

import java.util.Set;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.impl.EverythingPreconcept;

public class OntologicalCommitment extends Concept implements IConcept {

	public OntologicalCommitment(Set<IContextObject> extent) {
		super(new EverythingPreconcept(extent));
	}

}
