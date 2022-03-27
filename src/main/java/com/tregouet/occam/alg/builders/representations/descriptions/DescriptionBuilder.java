package com.tregouet.occam.alg.builders.representations.descriptions;

import java.util.function.Function;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.representations.descriptions.differentiae.DifferentiaeBuilder;
import com.tregouet.occam.alg.setters.SettersAbstractFactory;
import com.tregouet.occam.alg.setters.parameters.differentiae_coeff.DifferentiaeCoeffSetter;
import com.tregouet.occam.alg.setters.weighs.differentiae.DifferentiaeWeigher;
import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;

public interface DescriptionBuilder extends Function<IRepresentationTransitionFunction, IDescription> {
	
	public static DifferentiaeBuilder getDifferentiaeBuilder() {
		return GeneratorsAbstractFactory.INSTANCE.getDifferentiaeBuilder();
	}
	
	public static DifferentiaeCoeffSetter getDifferentiaeCoeffSetter() {
		return SettersAbstractFactory.INSTANCE.getDifferentiaeCoeffSetter();
	}
	
	public static DifferentiaeWeigher getDifferentiaeWeigher() {
		return SettersAbstractFactory.INSTANCE.getDifferentiaeWeigher();
	}

}
