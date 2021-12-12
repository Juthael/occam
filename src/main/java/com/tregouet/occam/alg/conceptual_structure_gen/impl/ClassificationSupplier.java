package com.tregouet.occam.alg.conceptual_structure_gen.impl;

import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

import com.tregouet.occam.alg.conceptual_structure_gen.IClassificationSupplier;
import com.tregouet.occam.data.concepts.IClassification;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.impl.Classification;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.tree_finder.algo.unidimensional_sorting.IUnidimensionalSorter;
import com.tregouet.tree_finder.algo.unidimensional_sorting.impl.UnidimensionalSorter;
import com.tregouet.tree_finder.data.UpperSemilattice;

public class ClassificationSupplier implements IClassificationSupplier {

	private final IUnidimensionalSorter<IConcept, IsA> categorySorter;
	private final List<IConcept> singletons;
	private final IConcept ontologicalCommitment;
	
	public ClassificationSupplier(UpperSemilattice<IConcept, IsA> conceptUSL, List<IConcept> singletons,
			IConcept ontologicalCommitment) throws IOException {
		try {
			this.categorySorter = new UnidimensionalSorter<>(conceptUSL);
		} catch (IOException e) {
			throw new IOException("ClassificationSupplier() : error." + System.lineSeparator() + e.getMessage());
		}
		this.singletons = singletons;
		this.ontologicalCommitment = ontologicalCommitment;
	}

	@Override
	public boolean hasNext() {
		return categorySorter.hasNext();
	}

	@Override
	public IClassification next() {
		return new Classification(IClassificationSupplier.commit(categorySorter.next(), ontologicalCommitment), 
				singletons);
	}

	@Override
	public TreeSet<IClassification> getRemainingClassifications() {
		TreeSet<IClassification> remainingClassifications = new TreeSet<>();
		while (hasNext())
			remainingClassifications.add(next());
		return remainingClassifications;
	}

}
