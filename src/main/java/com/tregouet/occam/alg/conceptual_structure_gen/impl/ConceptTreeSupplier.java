package com.tregouet.occam.alg.conceptual_structure_gen.impl;

import java.util.List;

import com.tregouet.occam.alg.conceptual_structure_gen.IConceptTreeSupplier;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.tree_finder.algo.unidimensional_sorting.IUnidimensionalSorter;
import com.tregouet.tree_finder.data.Tree;

public class ConceptTreeSupplier implements IConceptTreeSupplier {

	private final IUnidimensionalSorter<IConcept, IsA> categorySorter;
	private final IConcept ontologicalCommitment;
	
	public ConceptTreeSupplier(IUnidimensionalSorter<IConcept, IsA> categorySorter, 
			IConcept ontologicalCommitment) {
		this.categorySorter = categorySorter;
		this.ontologicalCommitment = ontologicalCommitment;
	}

	@Override
	public List<Tree<IConcept, IsA>> getSortingTrees() {
		return categorySorter.getSortingTrees();
	}

	@Override
	public boolean hasNext() {
		return categorySorter.hasNext();
	}

	@Override
	public Tree<IConcept, IsA> next() {
		return categorySorter.next();
	}

	@Override
	public Tree<IConcept, IsA> nextOntologicalCommitment() {
		return IConceptTreeSupplier.proceedToOntologicalCommitment(categorySorter.next(), ontologicalCommitment);
	}

}
