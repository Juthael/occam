package com.tregouet.occam.alg.concepts_gen;

import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.tree_finder.data.Tree;

public interface IConceptTreeSupplier extends Iterator<Tree<IConcept, IIsA>> {
	
	public static Tree<IConcept, IIsA> commit(
			Tree<IConcept, IIsA> classificationTree, IConcept ontologicalCommitment) {
		classificationTree.addAsNewRoot(ontologicalCommitment, true);
		return classificationTree;
	}
	
	List<Tree<IConcept, IIsA>> getRemainingTreesOfConcepts();

}
