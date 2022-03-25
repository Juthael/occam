package com.tregouet.occam.alg.builders.concepts.trees.impl;

import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.occam.alg.builders.concepts.trees.IConceptTreeBuilder;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.tree_finder.data.Tree;

public abstract class ConceptTreeBuilder implements IConceptTreeBuilder {
	
	public static Tree<IConcept, IIsA> commit(
			Tree<IConcept, IIsA> classificationTree, IConcept ontologicalCommitment) {
		classificationTree.addAsNewRoot(ontologicalCommitment, true);
		return classificationTree;
	}	

}
