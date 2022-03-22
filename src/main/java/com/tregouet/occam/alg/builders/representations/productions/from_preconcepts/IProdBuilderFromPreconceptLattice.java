package com.tregouet.occam.alg.builders.representations.productions.from_preconcepts;

import java.util.Set;

import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.preconcepts.IPreconceptLattice;

public interface IProdBuilderFromPreconceptLattice {
	
	IProdBuilderFromPreconceptLattice input(IPreconceptLattice preconceptLattice);
	
	Set<IContextualizedProduction> output();

}
