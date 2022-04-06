package com.tregouet.occam.data.representations;

import java.util.SortedSet;

import com.tregouet.occam.data.representations.concepts.IConceptLattice;

public interface IRepresentations {
	
	SortedSet<IRepresentation> getSortedRepresentations();
	
	IConceptLattice getConceptLattice();

}
