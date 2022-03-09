package com.tregouet.occam.data.concepts.transitions.dimensions;

import com.tregouet.occam.data.languages.generic.impl.Variable;

public class OntologicalCommitment extends Variable {
	
	public static final OntologicalCommitment INSTANCE = new OntologicalCommitment();
	
	private OntologicalCommitment() {
		super("∃");
	}

}
