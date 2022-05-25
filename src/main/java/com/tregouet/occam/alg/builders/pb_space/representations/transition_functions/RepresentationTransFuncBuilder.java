package com.tregouet.occam.alg.builders.pb_space.representations.transition_functions;

import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.productions.from_denotations.ProdBuilderFromDenotations;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.transition_saliences.TransitionSalienceSetter;
import com.tregouet.occam.data.problem_space.states.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.problem_space.states.transitions.productions.IContextualizedProduction;
import com.tregouet.tree_finder.data.InvertedTree;

@FunctionalInterface
public interface RepresentationTransFuncBuilder extends
		BiFunction<InvertedTree<IConcept, IIsA>, Set<IContextualizedProduction>, IRepresentationTransitionFunction> {

	/**
	 *  A transition can only occur from a concept to one of its direct subordinate
	 */
	public static TransitionSalienceSetter transitionSalienceSetter() {
		return BuildersAbstractFactory.INSTANCE.getTransitionSalienceSetter();
	}
	
	public static ProdBuilderFromDenotations getProdBuilderFromDenotations() {
		return BuildersAbstractFactory.INSTANCE.getProdBuilderFromDenotations();
	}

}
