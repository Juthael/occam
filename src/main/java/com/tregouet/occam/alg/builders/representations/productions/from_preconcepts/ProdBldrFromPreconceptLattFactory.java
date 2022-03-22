package com.tregouet.occam.alg.builders.representations.productions.from_preconcepts;

import com.tregouet.occam.alg.builders.representations.productions.from_preconcepts.impl.IfIsAThenBuildProductions;

public class ProdBldrFromPreconceptLattFactory {
	
	public static final ProdBldrFromPreconceptLattFactory INSTANCE = new ProdBldrFromPreconceptLattFactory();
	
	private ProdBldrFromPreconceptLattFactory() {
	}
	
	public IProdBuilderFromPreconceptLattice apply(ProdConstrFromPreconceptLattStrategy strategy) {
		switch(strategy) {
			case IF_SUBORDINATE_THEN_BUILD_PRODUCTIONS :
				return new IfIsAThenBuildProductions();
			default : 
				return null;
		}
	}

}
