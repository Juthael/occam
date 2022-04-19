package com.tregouet.occam.alg.builders.representations.transition_functions;

import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.representations.transition_functions.transition_saliences.TransitionSalienceSetter;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;
import com.tregouet.tree_finder.data.InvertedTree;

@FunctionalInterface
public interface RepresentationTransFuncBuilder extends
		BiFunction<InvertedTree<IConcept, IIsA>, Set<IContextualizedProduction>, IRepresentationTransitionFunction> {

	/**
	 *  A transition can only occur from a concept to one of its direct subordinate
	 */
	public static TransitionSalienceSetter transitionSalienceSetter() {
		return GeneratorsAbstractFactory.INSTANCE.getTransitionSalienceSetter();
	}

}
