package com.tregouet.occam.data.representations.properties;

import java.util.Set;

import com.tregouet.occam.alg.scorers.IWeighed;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.representations.properties.transitions.IApplication;

public interface IProperty extends IWeighed {
	
	IDenotation getFunction();
	
	Set<IApplication> getApplications();
	
	Set<IDenotation> getResultingValues();

}
