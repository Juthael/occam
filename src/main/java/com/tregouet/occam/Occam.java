package com.tregouet.occam;

import com.tregouet.occam.alg.OverallAbstractFactory;
import com.tregouet.occam.alg.OverallStrategy;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;

public class Occam {

	public static final OverallStrategy strategy = OverallStrategy.OVERALL_STRATEGY_3;

	public Occam() {
	}

	public static void initialize() {
		IContextObject.initializeIDGenerator();
		IConcept.initializeIDGenerator();
		IRepresentation.initializeIDGenerator();
	}

	public static void main(String[] args) {
		OverallAbstractFactory.INSTANCE.apply(strategy);
		new PrototypeMenu();
	}

}
