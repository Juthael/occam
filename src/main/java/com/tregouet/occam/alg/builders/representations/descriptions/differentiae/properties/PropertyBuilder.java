package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.properties;

import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.alg.setters.weights.properties.PropertyWeigher;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.structures.representations.productions.IContextualizedProduction;

public interface PropertyBuilder extends Function<Set<IContextualizedProduction>, Set<IProperty>> {

	PropertyBuilder setUp(IClassification classification, PropertyWeigher propWeigher);

}
