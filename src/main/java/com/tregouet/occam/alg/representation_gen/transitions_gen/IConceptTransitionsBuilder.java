package com.tregouet.occam.alg.representation_gen.transitions_gen;

import java.util.Set;

import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;

public interface IConceptTransitionsBuilder {
	
	Set<IConceptTransition> buildTransitionsFrom(Set<IContextualizedProduction> contextualizedProductions);

}
