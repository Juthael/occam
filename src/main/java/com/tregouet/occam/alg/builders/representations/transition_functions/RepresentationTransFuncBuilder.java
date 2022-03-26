package com.tregouet.occam.alg.builders.representations.transition_functions;

import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.representations.transition_functions.transition_saliences.TransitionSalienceSetter;
import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;
import com.tregouet.tree_finder.data.Tree;

@FunctionalInterface
public interface RepresentationTransFuncBuilder 
	extends BiFunction<Tree<IConcept, IIsA>, Set<IContextualizedProduction>, IRepresentationTransitionFunction> {
	
	//a transition can only occur from a concept to one of its direct subordinate
	
	public static TransitionSalienceSetter transitionSalienceSetter() {
		return GeneratorsAbstractFactory.INSTANCE.getTransitionSalienceSetter();
	}

}
