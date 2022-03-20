package com.tregouet.occam.alg.generation.representation.transitions_gen;

import java.util.Set;

import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.tree_finder.data.Tree;

public interface ITransitionsConstructionManager {
	
	ITransitionsConstructionManager input(Tree<IPreconcept, IIsA> treeOfPreconcepts,
			Set<IContextualizedProduction> unfilteredProductions);
	
	Set<IConceptTransition> output();

}
