package com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations;

import java.util.List;

import com.tregouet.occam.alg.setters.weights.Weighed;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IWeighedProperty;

public interface IDifferentiation extends Weighed {
	
	List<IWeighedProperty> getProperties();
	
	int nbOfProperties();
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object o);

}
