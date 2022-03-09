package com.tregouet.occam.data.concepts;

import java.util.Set;

import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedProduction;
import com.tregouet.occam.data.automata.states.IPushdownAutomatonState;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.partitions.IPartition;

public interface IConcept extends IPushdownAutomatonState<ContextualizedProduction, AVariable> {
	
	void setSpeciesDistinctiveFeaturesSaliency();
	
	void setSpeciesAlignableDifferencesSaliency();
	
	IPartition getConceptPartition();
	
	Set<IPartition> getSubPartitions();
	
	Set<IPartition> getAllPartitions();
	
	int getLowestSubordinateID();

}
