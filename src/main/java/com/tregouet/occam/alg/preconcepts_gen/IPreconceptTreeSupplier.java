package com.tregouet.occam.alg.preconcepts_gen;

import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.denotations.IPreconcept;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.tree_finder.data.Tree;

public interface IPreconceptTreeSupplier extends Iterator<Tree<IPreconcept, IIsA>> {
	
	public static Tree<IPreconcept, IIsA> commit(
			Tree<IPreconcept, IIsA> classificationTree, IPreconcept ontologicalCommitment) {
		classificationTree.addAsNewRoot(ontologicalCommitment, true);
		return classificationTree;
	}
	
	List<Tree<IPreconcept, IIsA>> getRemainingTreesOfConcepts();

}
