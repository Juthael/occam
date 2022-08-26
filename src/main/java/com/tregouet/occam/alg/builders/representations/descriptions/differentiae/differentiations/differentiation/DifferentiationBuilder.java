package com.tregouet.occam.alg.builders.representations.descriptions.differentiae.differentiations.differentiation;

import java.util.List;
import java.util.function.BiFunction;

import com.tregouet.occam.alg.setters.SettersAbstractFactory;
import com.tregouet.occam.alg.setters.weights.properties.PropertyWeigher;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations.IDifferentiation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;

public interface DifferentiationBuilder extends BiFunction<List<IProperty>, IClassification, IDifferentiation> {
	
	public static PropertyWeigher propertyWeigher() {
		return SettersAbstractFactory.INSTANCE.getPropertyWeigher();
	}

}
