package com.tregouet.occam.data.representations.impl;

import java.util.HashSet;
import java.util.Set;
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

	@Override
	public ICompleteRepresentation get(int iD) {
		for (ICompleteRepresentation representation : representations) {
			if (representation.id() == iD)
				return representation;
		}
		return null;
	}

	@Override
	public Set<ICompleteRepresentation> get(Set<Integer> iDs) {
		Set<ICompleteRepresentation> representations = new HashSet<>();
		for (Integer iD : iDs) {
			ICompleteRepresentation nextRep = get(iD);
			if (nextRep == null)
				return null;
			else representations.add(nextRep);
		}
		return representations;
	}

}
