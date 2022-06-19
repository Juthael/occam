package com.tregouet.occam.alg.builders.pb_space.representations.descriptions;

import java.util.Set;
import java.util.function.BiFunction;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.differentiae.DifferentiaeBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.metrics.RelativeSimilarityMetricsBuilder;
import com.tregouet.occam.alg.setters.SettersAbstractFactory;
import com.tregouet.occam.alg.setters.differentiae_coeff.DifferentiaeCoeffSetter;
import com.tregouet.occam.alg.setters.weighs.differentiae.DifferentiaeWeigher;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.descriptions.IDescription;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

/**
 * 2nd parameter : context particular ID to most specific concept ID in first parameter
 * @author Gael Tregouet
 *
 */
public interface DescriptionBuilder
	extends BiFunction<IClassification, Set<IContextualizedProduction>, IDescription> {

	public static DifferentiaeBuilder differentiaeBuilder() {
		return BuildersAbstractFactory.INSTANCE.getDifferentiaeBuilder();
	}

	public static DifferentiaeCoeffSetter differentiaeCoeffSetter() {
		return SettersAbstractFactory.INSTANCE.getDifferentiaeCoeffSetter();
	}

	public static DifferentiaeWeigher differentiaeWeigher() {
		return SettersAbstractFactory.INSTANCE.getDifferentiaeWeigher();
	}

	public static RelativeSimilarityMetricsBuilder relativeSimilarityMetricsBuilder() {
		return BuildersAbstractFactory.INSTANCE.getRelativeSimilarityMetricsBuilder();
	}

}
