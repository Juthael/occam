package com.tregouet.occam.alg.concepts_gen.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.alg.concepts_gen.IConceptTreeSupplier;
import com.tregouet.occam.data.denotations.IConcept;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.tree_finder.algo.unidimensional_sorting.IUnidimensionalSorter;
import com.tregouet.tree_finder.algo.unidimensional_sorting.impl.UnidimensionalSorter;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.data.UpperSemilattice;

public class DenotationSetsTreeSupplier implements IConceptTreeSupplier {

	private final IUnidimensionalSorter<IConcept, IIsA> denotationSetsSorter;
	private final IConcept ontologicalCommitment;
	
	public DenotationSetsTreeSupplier(UpperSemilattice<IConcept, IIsA> denotationSetUSL,
			IConcept ontologicalCommitment) throws IOException {
		try {
			this.denotationSetsSorter = new UnidimensionalSorter<>(denotationSetUSL);
		} catch (IOException e) {
			throw new IOException("DenotationSetsTreeSupplier() : error." + System.lineSeparator() + e.getMessage());
		}
		this.ontologicalCommitment = ontologicalCommitment;
	}

	@Override
	public List<Tree<IConcept, IIsA>> getRemainingTreesOfDenotationSets() {
		List<Tree<IConcept, IIsA>> remainingClassifications = new ArrayList<>();
		while (hasNext())
			remainingClassifications.add(next());
		return remainingClassifications;
	}

	@Override
	public boolean hasNext() {
		return denotationSetsSorter.hasNext();
	}

	@Override
	public Tree<IConcept, IIsA> next() {
		return IConceptTreeSupplier.commit(denotationSetsSorter.next(), ontologicalCommitment);
	}

}
