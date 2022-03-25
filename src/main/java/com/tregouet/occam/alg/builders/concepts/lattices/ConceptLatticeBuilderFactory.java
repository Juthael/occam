package com.tregouet.occam.alg.builders.concepts.lattices;

import com.tregouet.occam.alg.builders.concepts.lattices.impl.ConceptLatticeBuilder;

public class ConceptLatticeBuilderFactory {
	
	public static final ConceptLatticeBuilderFactory INSTANCE = new ConceptLatticeBuilderFactory();
	
	private ConceptLatticeBuilderFactory() {
	}
	
	public ConceptLatticeBuilder apply(ConceptLatticeConstructionStrategy strategy) {
		switch(strategy) {
			case GALOIS_CONNECTION : 
				return new ConceptLatticeBuilder();
			default : 
				return null;
		}
	}

}
