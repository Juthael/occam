package com.tregouet.occam.alg.builders.representations.concept_trees.impl;

import com.tregouet.occam.alg.builders.representations.concept_trees.ConceptTreeBuilder;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedTree;

public abstract class AbstractConceptTreeBuilder implements ConceptTreeBuilder {

	public static InvertedTree<IConcept, IIsA> commit(InvertedTree<IConcept, IIsA> classificationTree,
			IConcept ontologicalCommitment) {
		classificationTree.addAsNewRoot(ontologicalCommitment, true);
		return classificationTree;
	}

}
