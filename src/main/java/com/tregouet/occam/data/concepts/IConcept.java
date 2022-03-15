package com.tregouet.occam.data.concepts;

import java.util.Set;

import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.partitions.IPartition;
import com.tregouet.occam.data.preconcepts.IPreconcept;

public interface IConcept extends IState {
	
	IPartition getConceptPartition();
	
	Set<IPartition> getSubPartitions();	
	
	Set<IPartition> getAllPartitions();
	
	int getLowestSubordinateID();
	
	int getID();
	
	ConceptType type();
	
	IPreconcept getPreconcept();
	
	@Override
	boolean equals(Object other);
	
	@Override
	int hashCode();

}
