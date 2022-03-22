package com.tregouet.occam.alg.builders.preconcepts.lattices;

import com.tregouet.occam.alg.builders.preconcepts.lattices.impl.PreconceptLatticeBuilder;

public class PreconceptLatticeBuilderFactory {
	
	public static final PreconceptLatticeBuilderFactory INSTANCE = new PreconceptLatticeBuilderFactory();
	
	private PreconceptLatticeBuilderFactory() {
	}
	
	public PreconceptLatticeBuilder apply(PreconceptLatticeConstructionStrategy strategy) {
		switch(strategy) {
			case GALOIS_CONNECTION : 
				return new PreconceptLatticeBuilder();
			default : 
				return null;
		}
	}

}
