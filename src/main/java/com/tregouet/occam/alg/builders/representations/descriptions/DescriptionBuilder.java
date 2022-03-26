package com.tregouet.occam.alg.builders.representations.descriptions;

import java.util.function.Function;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.DifferentiaeBuilder;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;

public interface DescriptionBuilder extends Function<IRepresentationTransitionFunction, IDescription> {
	
	public static DifferentiaeBuilder getDifferentiaeBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getDifferentiaeBuilder();
	}

}
