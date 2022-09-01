package com.tregouet.occam.data.structures.representations.descriptions.differentiae.differentiations;

import java.util.List;

import com.tregouet.occam.alg.setters.weights.Weighed;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.IProperty;

public interface IDifferentiation extends Weighed {

	@Override
	boolean equals(Object o);

	List<IProperty> getProperties();

	@Override
	int hashCode();

	int nbOfProperties();

}
