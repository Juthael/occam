package com.tregouet.occam.alg.setters.differentiae_coeff.impl;

import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.setters.differentiae_coeff.DifferentiaeCoeffSetter;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public class SpeciesCardinality implements DifferentiaeCoeffSetter {

	private Tree<Integer, AbstractDifferentiae> classification = null;
	private Set<Integer> particularIDs = null;

	public SpeciesCardinality() {
	}

	@Override
	public void accept(AbstractDifferentiae diff) {
		double coeff = getSpeciesCardinality(diff.getTarget());
		diff.setWeightCoeff(coeff);
	}

	private int getSpeciesCardinality(Integer speciesID) {
		if (particularIDs.contains(speciesID))
			return 1;
		return Sets.intersection(classification.getDescendants(speciesID), particularIDs).size();
	}

	@Override
	public DifferentiaeCoeffSetter setContext(Tree<Integer, AbstractDifferentiae> classification) {
		this.classification = classification;
		this.particularIDs = classification.getLeaves();
		return this;
	}



}
