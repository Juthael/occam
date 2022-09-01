package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.differentiation;

import java.util.List;
import java.util.function.Function;

import com.tregouet.occam.alg.setters.weights.properties.PropertyWeigher;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.IDifferentiation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;

public interface DifferentiationBuilder extends Function<List<IProperty>, IDifferentiation> {

	public IDifferentiation apply(IProperty[] permutation);

	public DifferentiationBuilder setUp(PropertyWeigher propertyWeigher);

}
