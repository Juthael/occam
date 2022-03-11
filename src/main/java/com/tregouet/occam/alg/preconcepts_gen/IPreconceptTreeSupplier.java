package com.tregouet.occam.alg.preconcepts_gen;

import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.tree_finder.data.Tree;

public interface IPreconceptTreeSupplier extends Iterator<Tree<IPreconcept, IIsA>> {
	
	public static Tree<IPreconcept, IIsA> commit(
			Tree<IPreconcept, IIsA> classificationTree, IPreconcept ontologicalCommitment) {
		classificationTree.addAsNewRoot(ontologicalCommitment, true);
		return classificationTree;
	}
	
	List<Tree<IPreconcept, IIsA>> getRemainingTreesOfPreconcepts();

}
