package com.tregouet.occam.alg.builders.pb_space.representations.descriptions;

import java.util.function.BiFunction;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.DifferentiaeBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.metrics.SimilarityMetricsBuilder;
import com.tregouet.occam.alg.setters.SettersAbstractFactory;
import com.tregouet.occam.alg.setters.differentiae_coeff.DifferentiaeCoeffSetter;
import com.tregouet.occam.alg.setters.weighs.differentiae.DifferentiaeWeigher;
import com.tregouet.occam.data.problem_space.states.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.descriptions.IDescription;
import com.tregouet.occam.data.problem_space.states.transitions.IRepresentationTransitionFunction;
import com.tregouet.tree_finder.data.InvertedTree;

/**
 * 2nd parameter : context particular ID to most specific concept ID in first parameter
 * @author Gael Tregouet
 *
 */
public interface DescriptionBuilder 
	extends BiFunction<IRepresentationTransitionFunction, InvertedTree<IConcept, IIsA>, IDescription> {

	public static DifferentiaeBuilder differentiaeBuilder() {
		return BuildersAbstractFactory.INSTANCE.getDifferentiaeBuilder();
	}

	public static DifferentiaeCoeffSetter differentiaeCoeffSetter() {
		return SettersAbstractFactory.INSTANCE.getDifferentiaeCoeffSetter();
	}

	public static DifferentiaeWeigher differentiaeWeigher() {
		return SettersAbstractFactory.INSTANCE.getDifferentiaeWeigher();
	}
	
	public static SimilarityMetricsBuilder similarityMetricsBuilder() {
		return BuildersAbstractFactory.INSTANCE.getSimilarityMetricsBuilder();
	}

}
