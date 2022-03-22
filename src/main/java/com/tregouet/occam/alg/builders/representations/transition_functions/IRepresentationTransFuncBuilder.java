package com.tregouet.occam.alg.builders.representations.transition_functions;

import java.util.Set;

import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;
import com.tregouet.tree_finder.data.Tree;

public interface IRepresentationTransFuncBuilder {
	
	IRepresentationTransFuncBuilder input(Tree<IPreconcept, IIsA> treeOfPreconcepts,
			Set<IContextualizedProduction> unfilteredUnreducedProds);
	
	IRepresentationTransitionFunction output();

}
