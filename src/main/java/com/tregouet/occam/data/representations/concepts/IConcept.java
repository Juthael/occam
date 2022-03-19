package com.tregouet.occam.data.representations.concepts;

import java.util.Set;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.states.IPushdownAutomatonState;
import com.tregouet.occam.data.partitions.IPartition;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.representations.properties.transitions.IApplication;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionOIC;

public interface IConcept extends  IPushdownAutomatonState<
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
	
	void setSubconceptsDistinctiveFeaturesSalience();
	
	void setSpeciesAlignableDifferencesSalience();
	
	void setSaliences();
	
	Set<IApplication> getApplications();
	
	Set<IConceptTransition> getConceptTransitions();	
	
	boolean isOperative();

}
