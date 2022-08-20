package com.tregouet.occam.alg.setters.weighs.differentiae.coeff.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.setters.weighs.differentiae.coeff.DifferentiaeCoeffSetter;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IComplementaryConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.utils.Functions;

public class SpeciesCardinality implements DifferentiaeCoeffSetter {

	private InvertedTree<IConcept, IIsA> conceptTree = null;

	public SpeciesCardinality() {
	}

	@Override
	public void accept(ADifferentiae diff) {
		if (diff.getGenusID() == IConcept.ONTOLOGICAL_COMMITMENT_ID)
			diff.setWeightCoeff(1.0);
		else {
			double coeff = getSpeciesCardinality(diff.getTarget());
			diff.setWeightCoeff(coeff);
		}
	}

	@Override
	public DifferentiaeCoeffSetter setContext(InvertedTree<IConcept, IIsA> conceptTree) {
		this.conceptTree = conceptTree;
		return this;
	}

	private int getSpeciesCardinality(Integer speciesID) {
		int cardinality = 0;
		for (IConcept concept : conceptTree.vertexSet()) {
			if (concept.iD() == speciesID.intValue()) {
				Set<IConcept> upperBounds = Functions.upperSet(conceptTree, concept);
				Set<Integer> closedIDs = new HashSet<>();
				for (IConcept upperBound : upperBounds) {
					if (upperBound.isComplementary()) {
						IConcept complemented = ((IComplementaryConcept) upperBound).getComplemented();
						closedIDs.addAll(complemented.getMaxExtentIDs());
					}
				}
				for (Integer particularID : concept.getMaxExtentIDs()) {
					if (!closedIDs.contains(particularID))
						cardinality ++;
				}
				return cardinality;
			}
		}
		return -1; //never happens
	}

}
