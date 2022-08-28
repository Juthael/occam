package com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties;

import java.util.Set;

import com.tregouet.occam.alg.setters.weights.Weighed;
import com.tregouet.occam.data.structures.representations.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.properties.computations.IComputation;

public interface IProperty extends Weighed {

	@Override
	boolean equals(Object o);

	Set<IComputation> getComputations();

	IDenotation getFunction();

	int getGenusID();

	int getSpeciesID();

	@Override
	int hashCode();

	boolean isBlank();

	void setWeight(double weight);

	@Override
	String toString();

}
