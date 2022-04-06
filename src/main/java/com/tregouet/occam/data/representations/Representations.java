package com.tregouet.occam.data.representations;

import java.util.SortedSet;

import com.tregouet.occam.data.representations.concepts.IConceptLattice;

public class Representations implements IRepresentations {
	
	private final IConceptLattice conceptLattice;
	private final SortedSet<IRepresentation> representations;
	
	public Representations(IConceptLattice conceptLattice, SortedSet<IRepresentation> representations) {
		this.conceptLattice = conceptLattice;
		this.representations = representations;
	}

	@Override
	public SortedSet<IRepresentation> getSortedRepresentations() {
		return representations;
	}

	@Override
	public IConceptLattice getConceptLattice() {
		return conceptLattice;
	}

}
