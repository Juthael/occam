package com.tregouet.occam.data.representations.descriptions.properties;

import java.util.Set;

import com.tregouet.occam.alg.setters.weighs.Weighed;
import com.tregouet.occam.data.representations.concepts.denotations.IDenotation;
import com.tregouet.occam.data.representations.transitions.IApplication;

public interface IProperty extends Weighed {

	Set<IApplication> getApplications();

	IDenotation getFunction();

	Set<IDenotation> getResultingValues();

	void setWeight(double weight);

	@Override
	String toString();
	
	@Override
	int hashCode();
	
	@Override
	boolean equals(Object o);
	
	int getGenusID();
	
	int getSpeciesID();

}
