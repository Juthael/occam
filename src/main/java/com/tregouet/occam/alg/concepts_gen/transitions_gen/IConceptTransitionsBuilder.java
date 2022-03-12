package com.tregouet.occam.alg.concepts_gen.transitions_gen;

import java.util.Set;

import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.concepts.transitions.IConceptTransition;

public interface IConceptTransitionsBuilder {
	
	Set<IConceptTransition> buildTransitionsFrom(Set<IContextualizedProduction> contextualizedProductions);

}
