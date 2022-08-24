package com.tregouet.occam.alg.setters.weights.properties;

import com.tregouet.occam.alg.setters.weights.Weigher;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;

public interface PropertyWeigher extends Weigher<IProperty> {

	PropertyWeigher setUp(IClassification classification);

}
