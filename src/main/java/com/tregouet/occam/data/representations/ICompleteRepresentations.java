package com.tregouet.occam.data.representations;

import java.util.Set;
import java.util.SortedSet;

import com.tregouet.occam.data.representations.concepts.IConceptLattice;

public interface ICompleteRepresentations {

	ICompleteRepresentation get(int iD);

	Set<ICompleteRepresentation> get(Set<Integer> iDs);

	IConceptLattice getConceptLattice();

	SortedSet<ICompleteRepresentation> getSortedRepresentations();

}
