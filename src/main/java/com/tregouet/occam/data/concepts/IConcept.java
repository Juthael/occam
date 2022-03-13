package com.tregouet.occam.data.concepts;

import java.util.Set;

import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.states.IPushdownAutomatonState;
import com.tregouet.occam.data.concepts.transitions.IConceptTransition;
import com.tregouet.occam.data.concepts.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.concepts.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.partitions.IPartition;
import com.tregouet.occam.data.preconcepts.IPreconcept;

public interface IConcept extends IPushdownAutomatonState<
	IContextualizedProduction, 
	AVariable, 
	IConceptTransitionIC, 
	IConceptTransitionOIC, 
	IConceptTransition> {
	
	IPartition getConceptPartition();
	
	Set<IPartition> getSubPartitions();	
	
	Set<IPartition> getAllPartitions();
	
	int getLowestSubordinateID();
	
	boolean isUniversal();
	
	int getID();
	
	ConceptualType type();
	
	IPreconcept getPreconcept();

}
