package com.tregouet.occam.data.representations.properties;

import java.util.List;

import com.tregouet.occam.alg.scores_calc.IWeighed;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.representations.properties.transitions.IApplication;

public interface IProperty extends IWeighed {
	
	IDenotation getFunction();
	
	List<IApplication> getApplications();
	
	List<IDenotation> getResultingValues();

}
