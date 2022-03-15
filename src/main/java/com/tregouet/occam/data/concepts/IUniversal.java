package com.tregouet.occam.data.concepts;

import java.util.Set;

import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.states.IPushdownAutEvaluatingState;
import com.tregouet.occam.data.concepts.transitions.IApplication;
import com.tregouet.occam.data.concepts.transitions.IConceptTransition;
import com.tregouet.occam.data.concepts.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.concepts.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.languages.generic.AVariable;

public interface IUniversal extends IPushdownAutEvaluatingState<
	IContextualizedProduction, 
	AVariable, 
	IConceptTransitionIC, 
	IConceptTransitionOIC, 
	IConceptTransition> {
	
	void setSubconceptsDistinctiveFeaturesSaliency();
	
	void setSpeciesAlignableDifferencesSaliency();
	
	void setSaliencies();
	
	Set<IApplication> getApplications();
	
	Set<IConceptTransition> getConceptTransitions();	

}
