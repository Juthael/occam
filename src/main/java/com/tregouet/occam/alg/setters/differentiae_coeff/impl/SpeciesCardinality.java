package com.tregouet.occam.alg.setters.differentiae_coeff.impl;

import com.tregouet.occam.alg.setters.differentiae_coeff.DifferentiaeCoeffSetter;
import com.tregouet.occam.data.problem_space.states.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.InvertedTree;

public class SpeciesCardinality implements DifferentiaeCoeffSetter {

	private InvertedTree<IConcept, IIsA> conceptTree = null;

	public SpeciesCardinality() {
	}

	@Override
	public void accept(AbstractDifferentiae diff) {
		double coeff = getSpeciesCardinality(diff.getTarget());
		diff.setWeightCoeff(coeff);
	}

	@Override
	public DifferentiaeCoeffSetter setContext(InvertedTree<IConcept, IIsA> conceptTree) {
		this.conceptTree = conceptTree;
		return this;
	}

	private int getSpeciesCardinality(Integer speciesID) {
		for (IConcept concept : conceptTree.vertexSet()) {
			if (concept.iD() == speciesID.intValue())
				return concept.getExtentIDs().size();
		}
		return -1; //never happens
	}

}
