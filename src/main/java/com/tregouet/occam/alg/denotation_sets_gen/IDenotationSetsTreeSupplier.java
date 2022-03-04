package com.tregouet.occam.alg.denotation_sets_gen;

import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.denotations.IConcept;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.tree_finder.data.Tree;

public interface IDenotationSetsTreeSupplier extends Iterator<Tree<IConcept, IIsA>> {
	
	public static Tree<IConcept, IIsA> commit(
			Tree<IConcept, IIsA> classificationTree, IConcept ontologicalCommitment) {
		classificationTree.addAsNewRoot(ontologicalCommitment, true);
		return classificationTree;
	}
	
	List<Tree<IConcept, IIsA>> getRemainingTreesOfDenotationSets();

}
