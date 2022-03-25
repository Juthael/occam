package com.tregouet.occam.alg.builders.representations.productions.from_concepts;

import java.util.Set;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;

public interface IProdBuilderFromConceptLattice {
	
	IProdBuilderFromConceptLattice input(IConceptLattice conceptLattice);
	
	Set<IContextualizedProduction> output();

}
