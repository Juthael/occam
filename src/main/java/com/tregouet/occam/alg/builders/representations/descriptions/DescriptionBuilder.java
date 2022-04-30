package com.tregouet.occam.alg.builders.representations.descriptions;

import java.util.Map;
import java.util.function.BiFunction;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.problem_spaces.partial_representations.metrics.SimilarityPartialMetricsBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.DifferentiaeBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.metrics.SimilarityMetricsBuilder;
import com.tregouet.occam.alg.setters.SettersAbstractFactory;
import com.tregouet.occam.alg.setters.differentiae_coeff.DifferentiaeCoeffSetter;
import com.tregouet.occam.alg.setters.weighs.differentiae.DifferentiaeWeigher;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;

public interface DescriptionBuilder 
	extends BiFunction<IRepresentationTransitionFunction, Map<Integer, Integer>, IDescription> {

	public static DifferentiaeBuilder differentiaeBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getDifferentiaeBuilder();
	}

	public static DifferentiaeCoeffSetter differentiaeCoeffSetter() {
		return SettersAbstractFactory.INSTANCE.getDifferentiaeCoeffSetter();
	}

	public static DifferentiaeWeigher differentiaeWeigher() {
		return SettersAbstractFactory.INSTANCE.getDifferentiaeWeigher();
	}

	public static SimilarityMetricsBuilder similarityMetricsBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getSimilarityMetricsBuilder();
	}
	
	public static SimilarityPartialMetricsBuilder similarityPartialMetricsBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getSimilarityPartialMetricsBuilder();
	}

}
