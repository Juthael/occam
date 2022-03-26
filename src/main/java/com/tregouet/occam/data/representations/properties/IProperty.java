package com.tregouet.occam.data.representations.properties;

import java.util.Set;

import com.tregouet.occam.alg.weighers.IWeighed;
import com.tregouet.occam.data.representations.concepts.IDenotation;
import com.tregouet.occam.data.representations.properties.transitions.IApplication;

public interface IProperty extends IWeighed {
	
	IDenotation getFunction();
	
	Set<IApplication> getApplications();
	
	Set<IDenotation> getResultingValues();
	
	void setWeight(double weight);

}
