package com.tregouet.occam.alg.conceptual_structure_gen;

import java.util.Iterator;
import java.util.TreeSet;

import com.tregouet.occam.data.concepts.IClassification;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.tree_finder.data.Tree;

public interface IClassificationSupplier extends Iterator<IClassification> {
	
	public static Tree<IConcept, IsA> commit(
			Tree<IConcept, IsA> classificationTree, IConcept ontologicalCommitment) {
		classificationTree.addAsNewRoot(ontologicalCommitment, true);
		return classificationTree;
	}
	
	TreeSet<IClassification> getRemainingClassifications();

}
