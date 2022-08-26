package com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties;

import com.tregouet.occam.alg.setters.weights.Weighed;

public interface IWeighedProperty extends IProperty, Weighed {
	
	void setWeight(double weight);
	
	@Override
	boolean equals(Object o);
	
	@Override
	int hashCode();

}
