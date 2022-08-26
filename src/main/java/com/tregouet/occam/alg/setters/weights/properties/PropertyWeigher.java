package com.tregouet.occam.alg.setters.weights.properties;

import com.tregouet.occam.alg.setters.weights.Weigher;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IWeighedProperty;

public interface PropertyWeigher extends Weigher<IWeighedProperty> {

	PropertyWeigher setUp(IClassification classification);

}
