package com.tregouet.occam.alg.builders.pb_space.impl;

import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorer;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedTree;

public class AutomaticallyExpandTrivialLeaves extends RemoveUninformative implements ProblemSpaceExplorer {
	
	@Override
	protected void expandForbiddenLeaves(Set<IRepresentation> newRepresentations) {
		for (IRepresentation representation : newRepresentations) {
			if (isATrivialLeaf(representation))
				apply(representation.iD());
		}
	}
	
	private static boolean isATrivialLeaf(IRepresentation representation) {
		InvertedTree<IConcept, IIsA> tree = representation.getTreeOfConcepts();
		Set<IConcept> leaves = tree.getLeaves();
		for(IConcept leaf : leaves) {
			if (leaf.getExtentIDs().size() == 2)
				return true;
		}
		return false;
	}

}
