package com.tregouet.occam.alg.builders.pb_space.representations.transition_functions;

import java.util.function.BiFunction;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.lattice_productions.from_denotations.ProdBuilderFromDenotations;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.productions.IClassificationProductions;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;

@FunctionalInterface
public interface RepresentationTransFuncBuilder extends
		BiFunction<IClassification, IClassificationProductions, IRepresentationTransitionFunction> {

	public static ProdBuilderFromDenotations getProdBuilderFromDenotations() {
		return BuildersAbstractFactory.INSTANCE.getProdBuilderFromDenotations();
	}

}
