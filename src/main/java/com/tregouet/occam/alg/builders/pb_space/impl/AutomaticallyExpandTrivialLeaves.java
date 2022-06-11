package com.tregouet.occam.alg.builders.pb_space.impl;

import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorer;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;

public class AutomaticallyExpandTrivialLeaves extends RemoveUninformative implements ProblemSpaceExplorer {

	@Override
	protected void expandTransitoryLeaves(Set<IRepresentation> newRepresentations) {
		for (IRepresentation representation : newRepresentations) {
			if (isATrivialLeaf(representation))
				apply(representation.iD());
		}
	}

	private static boolean isATrivialLeaf(IRepresentation representation) {
		IClassification classification = representation.getClassification();
		for(IConcept leaf : classification.getMostSpecificConcepts()) {
			if (classification.getExtentIDs(leaf.iD()).size() == 2)
				return true;
		}
		return false;
	}

}
