package com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties;

import java.util.Set;

import com.tregouet.occam.alg.setters.weighs.Weighed;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.IApplication;

public interface IProperty extends Weighed {

	IDenotation getFunction();

	Set<IApplication> getApplications();

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
