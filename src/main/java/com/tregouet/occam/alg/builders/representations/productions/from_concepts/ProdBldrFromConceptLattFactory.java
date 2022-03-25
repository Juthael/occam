package com.tregouet.occam.alg.builders.representations.productions.from_concepts;

import com.tregouet.occam.alg.builders.representations.productions.from_concepts.impl.IfIsAThenBuildProductions;

public class ProdBldrFromConceptLattFactory {
	
	public static final ProdBldrFromConceptLattFactory INSTANCE = new ProdBldrFromConceptLattFactory();
	
	private ProdBldrFromConceptLattFactory() {
	}
	
	public IProdBuilderFromConceptLattice apply(ProdConstrFromConceptLattStrategy strategy) {
		switch(strategy) {
			case IF_SUBORDINATE_THEN_BUILD_PRODUCTIONS :
				return new IfIsAThenBuildProductions();
			default : 
				return null;
		}
	}

}
