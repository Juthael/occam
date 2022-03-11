package com.tregouet.occam.data.concepts.properties;

import java.util.List;

import com.tregouet.occam.alg.scores_calc.IWeighed;
import com.tregouet.occam.data.concepts.transitions.IApplication;
import com.tregouet.occam.data.preconcepts.IDenotation;

public interface IProperty extends IWeighed {
	
	IDenotation getFunction();
	
	List<IApplication> getApplications();
	
	List<IDenotation> getResultingValues();

}
