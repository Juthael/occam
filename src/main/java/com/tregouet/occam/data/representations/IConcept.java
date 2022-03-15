package com.tregouet.occam.data.representations;

import java.util.Set;

import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.states.IPushdownAutEvaluatingState;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.partitions.IPartition;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.representations.transitions.IApplication;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionOIC;

public interface IConcept extends  IPushdownAutEvaluatingState<
	IContextualizedProduction, 
	AVariable, 
	IConceptTransitionIC, 
	IConceptTransitionOIC, 
	IConceptTransition
	> {
	
	int getID();
	
	ConceptType type();
	
	IPreconcept getPreconcept();	
	
	IPartition getConceptPartition();
	
	Set<IPartition> getSubPartitions();	
	
	Set<IPartition> getAllPartitions();
	
	int getLowestSubordinateID();
	
	@Override
	boolean equals(Object other);
	
	@Override
	int hashCode();
	
	void setSubconceptsDistinctiveFeaturesSaliency();
	
	void setSpeciesAlignableDifferencesSaliency();
	
	void setSaliencies();
	
	Set<IApplication> getApplications();
	
	Set<IConceptTransition> getConceptTransitions();	

}
