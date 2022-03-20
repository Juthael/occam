package com.tregouet.occam.alg.generators.preconcepts.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.generators.preconcepts.IPreconceptTreeSupplier;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
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
	public Set<Tree<IPreconcept, IIsA>> getRemainingTreesOfPreconcepts() {
		Set<Tree<IPreconcept, IIsA>> remainingClassifications = new HashSet<>();
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
