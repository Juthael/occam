package com.tregouet.occam.alg.denotation_sets_gen.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.alg.denotation_sets_gen.IDenotationSetsTreeSupplier;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.tree_finder.algo.unidimensional_sorting.IUnidimensionalSorter;
import com.tregouet.tree_finder.algo.unidimensional_sorting.impl.UnidimensionalSorter;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.data.UpperSemilattice;

public class DenotationSetsTreeSupplier implements IDenotationSetsTreeSupplier {

	private final IUnidimensionalSorter<IDenotationSet, IIsA> denotationSetsSorter;
	private final IDenotationSet ontologicalCommitment;
	
	public DenotationSetsTreeSupplier(UpperSemilattice<IDenotationSet, IIsA> denotationSetUSL,
			IDenotationSet ontologicalCommitment) throws IOException {
		try {
			this.denotationSetsSorter = new UnidimensionalSorter<>(denotationSetUSL);
		} catch (IOException e) {
			throw new IOException("DenotationSetsTreeSupplier() : error." + System.lineSeparator() + e.getMessage());
		}
		this.ontologicalCommitment = ontologicalCommitment;
	}

	@Override
	public List<Tree<IDenotationSet, IIsA>> getRemainingTreesOfDenotationSets() {
		List<Tree<IDenotationSet, IIsA>> remainingClassifications = new ArrayList<>();
		while (hasNext())
			remainingClassifications.add(next());
		return remainingClassifications;
	}

	@Override
	public boolean hasNext() {
		return denotationSetsSorter.hasNext();
	}

	@Override
	public Tree<IDenotationSet, IIsA> next() {
		return IDenotationSetsTreeSupplier.commit(denotationSetsSorter.next(), ontologicalCommitment);
	}

}
