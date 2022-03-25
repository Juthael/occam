package com.tregouet.occam.alg.builders.representations.productions.from_concepts;

import java.util.Set;

import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.concepts.IConceptLattice;

public interface IProdBuilderFromConceptLattice {
	
	IProdBuilderFromConceptLattice input(IConceptLattice conceptLattice);
	
	Set<IContextualizedProduction> output();

}
