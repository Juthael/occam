package com.tregouet.occam.alg.builders.preconcepts.trees.impl;

import com.tregouet.occam.alg.builders.preconcepts.trees.IPreconceptTreeBuilder;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.tree_finder.data.Tree;

public abstract class PreconceptTreeBuilder implements IPreconceptTreeBuilder {
	
	public static Tree<IPreconcept, IIsA> commit(
			Tree<IPreconcept, IIsA> classificationTree, IPreconcept ontologicalCommitment) {
		classificationTree.addAsNewRoot(ontologicalCommitment, true);
		return classificationTree;
	}	

}
