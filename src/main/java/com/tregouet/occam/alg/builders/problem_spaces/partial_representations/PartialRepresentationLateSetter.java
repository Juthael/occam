package com.tregouet.occam.alg.builders.problem_spaces.partial_representations;

import java.util.function.Consumer;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.DescriptionBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.fact_evaluators.FactEvaluatorBuilder;
import com.tregouet.occam.data.representations.IPartialRepresentation;

public interface PartialRepresentationLateSetter extends Consumer<IPartialRepresentation> {

	public static DescriptionBuilder descriptionBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getDescriptionBuilder();
	}

	public static FactEvaluatorBuilder getfactEvaluatorBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getFactEvaluatorBuilder();
	}

}
