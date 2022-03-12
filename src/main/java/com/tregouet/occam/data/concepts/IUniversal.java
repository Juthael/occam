package com.tregouet.occam.data.concepts;

import java.util.Set;

import com.tregouet.occam.data.concepts.transitions.IApplication;
import com.tregouet.occam.data.concepts.transitions.IConceptTransition;

public interface IUniversal extends IConcept {
	
	void setSubconceptsDistinctiveFeaturesSaliency();
	
	void setSpeciesAlignableDifferencesSaliency();
	
	void setSaliencies();
	
	Set<IApplication> getApplications();
	
	Set<IConceptTransition> getConceptTransitions();	

}
