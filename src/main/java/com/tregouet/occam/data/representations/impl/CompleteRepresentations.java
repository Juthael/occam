package com.tregouet.occam.data.representations.impl;

import java.util.SortedSet;

import com.tregouet.occam.data.representations.ICompleteRepresentation;
import com.tregouet.occam.data.representations.ICompleteRepresentations;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;

public class CompleteRepresentations implements ICompleteRepresentations {
	
	private final IConceptLattice conceptLattice;
	private final SortedSet<ICompleteRepresentation> representations;
	
	public CompleteRepresentations(IConceptLattice conceptLattice, SortedSet<ICompleteRepresentation> representations) {
		this.conceptLattice = conceptLattice;
		this.representations = representations;
	}

	@Override
	public SortedSet<ICompleteRepresentation> getSortedRepresentations() {
		return representations;
	}

	@Override
	public IConceptLattice getConceptLattice() {
		return conceptLattice;
	}

}
