package com.tregouet.occam;

import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.OverallStrategy;
import com.tregouet.occam.data.structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;

public class Occam {

	public static final OverallStrategy STRATEGY = OverallStrategy.OVERALL_STRATEGY_4;

	public Occam() {
	}

	public static void initialize() {
		AVariable.resetVarNaming();
		IContextObject.initializeIDGenerator();
		IConcept.initializeIDGenerator();
		IRepresentation.initializeIDGenerator();
	}

	public static void main(String[] args) {
		OverallAbstractFactory.INSTANCE.apply(STRATEGY);
		new PrototypeMenu();
	}

}
