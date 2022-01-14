package com.tregouet.occam.alg.denotation_sets_gen;

import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.tree_finder.data.Tree;

public interface IDenotationSetsTreeSupplier extends Iterator<Tree<IDenotationSet, IIsA>> {
	
	public static Tree<IDenotationSet, IIsA> commit(
			Tree<IDenotationSet, IIsA> classificationTree, IDenotationSet ontologicalCommitment) {
		classificationTree.addAsNewRoot(ontologicalCommitment, true);
		return classificationTree;
	}
	
	List<Tree<IDenotationSet, IIsA>> getRemainingTreesOfDenotationSets();

}
