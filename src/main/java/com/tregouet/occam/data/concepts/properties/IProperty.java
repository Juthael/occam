package com.tregouet.occam.data.concepts.properties;

import java.util.List;

import com.tregouet.occam.alg.scores_calc.IWheighed;
import com.tregouet.occam.data.concepts.transitions.IApplication;
import com.tregouet.occam.data.preconcepts.IDenotation;

public interface IProperty extends IWheighed {
	
	IDenotation getFunction();
	
	List<IApplication> getApplications();
	
	List<IDenotation> getResultingValues();

}
