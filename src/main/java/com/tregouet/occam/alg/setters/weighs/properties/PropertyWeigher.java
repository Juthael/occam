package com.tregouet.occam.alg.setters.weighs.properties;

import com.tregouet.occam.alg.setters.weighs.Weigher;
import com.tregouet.occam.data.representations.classifications.IClassification;
import com.tregouet.occam.data.representations.descriptions.differentiae.properties.IProperty;

public interface PropertyWeigher extends Weigher<IProperty> {

	PropertyWeigher setUp(IClassification classification);

}
