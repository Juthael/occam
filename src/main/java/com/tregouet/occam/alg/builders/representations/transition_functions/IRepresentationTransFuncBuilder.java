package com.tregouet.occam.alg.builders.representations.transition_functions;

import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;
import com.tregouet.tree_finder.data.Tree;

@FunctionalInterface
public interface IRepresentationTransFuncBuilder 
	extends BiFunction<Tree<IConcept, IIsA>, Set<IContextualizedProduction>, IRepresentationTransitionFunction> {

}
