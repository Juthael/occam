package com.tregouet.occam.alg.builders.pb_space.concept_lattices;

import com.tregouet.occam.alg.builders.pb_space.concept_lattices.impl.GaloisConnection;

public class ConceptLatticeBuilderFactory {

	public static final ConceptLatticeBuilderFactory INSTANCE = new ConceptLatticeBuilderFactory();

	private ConceptLatticeBuilderFactory() {
	}

	public GaloisConnection apply(ConceptLatticeBuilderStrategy strategy) {
		switch (strategy) {
		case GALOIS_CONNECTION:
			return new GaloisConnection();
		default:
			return null;
		}
	}

}
