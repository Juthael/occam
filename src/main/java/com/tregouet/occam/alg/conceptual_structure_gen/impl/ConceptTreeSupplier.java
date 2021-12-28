package com.tregouet.occam.alg.conceptual_structure_gen.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.alg.conceptual_structure_gen.IConceptTreeSupplier;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.tree_finder.algo.unidimensional_sorting.IUnidimensionalSorter;
import com.tregouet.tree_finder.algo.unidimensional_sorting.impl.UnidimensionalSorter;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.data.UpperSemilattice;

public class ConceptTreeSupplier implements IConceptTreeSupplier {

	private final IUnidimensionalSorter<IConcept, IIsA> conceptSorter;
	private final IConcept ontologicalCommitment;
	
	public ConceptTreeSupplier(UpperSemilattice<IConcept, IIsA> conceptUSL,
			IConcept ontologicalCommitment) throws IOException {
		try {
			this.conceptSorter = new UnidimensionalSorter<>(conceptUSL);
		} catch (IOException e) {
			throw new IOException("ClassificationSupplier() : error." + System.lineSeparator() + e.getMessage());
		}
		this.ontologicalCommitment = ontologicalCommitment;
	}

	@Override
	public List<Tree<IConcept, IIsA>> getRemainingClassifications() {
		List<Tree<IConcept, IIsA>> remainingClassifications = new ArrayList<>();
		while (hasNext())
			remainingClassifications.add(next());
		return remainingClassifications;
	}

	@Override
	public boolean hasNext() {
		return conceptSorter.hasNext();
	}

	@Override
	public Tree<IConcept, IIsA> next() {
		return IConceptTreeSupplier.commit(conceptSorter.next(), ontologicalCommitment);
	}

}
