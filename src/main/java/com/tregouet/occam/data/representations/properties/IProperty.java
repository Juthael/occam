package com.tregouet.occam.data.representations.properties;

import java.util.Set;

import com.tregouet.occam.alg.setters.weighs.Weighed;
import com.tregouet.occam.data.representations.concepts.IDenotation;
import com.tregouet.occam.data.representations.properties.transitions.IApplication;

public interface IProperty extends Weighed {
	
	IDenotation getFunction();
	
	Set<IApplication> getApplications();
	
	Set<IDenotation> getResultingValues();
	
	void setWeight(double weight);

}
