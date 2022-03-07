package com.tregouet.occam.alg.preconcepts_gen.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.denotations.IPreconcept;
import com.tregouet.occam.alg.preconcepts_gen.IPreconceptTreeSupplier;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.tree_finder.algo.unidimensional_sorting.IUnidimensionalSorter;
import com.tregouet.tree_finder.algo.unidimensional_sorting.impl.UnidimensionalSorter;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.data.UpperSemilattice;

public class PreconceptTreeSupplier implements IPreconceptTreeSupplier {

	private final IUnidimensionalSorter<IPreconcept, IIsA> preconceptSorter;
	private final IPreconcept ontologicalCommitment;
	
	public PreconceptTreeSupplier(UpperSemilattice<IPreconcept, IIsA> conceptUSL,
			IPreconcept ontologicalCommitment) throws IOException {
		try {
			this.preconceptSorter = new UnidimensionalSorter<>(conceptUSL);
		} catch (IOException e) {
			throw new IOException("ConceptTreeSupplier() : error." + System.lineSeparator() + e.getMessage());
		}
		this.ontologicalCommitment = ontologicalCommitment;
	}

	@Override
	public List<Tree<IPreconcept, IIsA>> getRemainingTreesOfConcepts() {
		List<Tree<IPreconcept, IIsA>> remainingClassifications = new ArrayList<>();
		while (hasNext())
			remainingClassifications.add(next());
		return remainingClassifications;
	}

	@Override
	public boolean hasNext() {
		return preconceptSorter.hasNext();
	}

	@Override
	public Tree<IPreconcept, IIsA> next() {
		return IPreconceptTreeSupplier.commit(preconceptSorter.next(), ontologicalCommitment);
	}

}
