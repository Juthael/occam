package com.tregouet.occam.alg.builders.representations.concept_trees.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.builders.representations.concept_trees.ConceptTreeBuilder;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.tree_finder.algo.unidimensional_sorting.IUnidimensionalSorter;
import com.tregouet.tree_finder.algo.unidimensional_sorting.impl.UnidimensionalSorter;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.data.InvertedUpperSemilattice;

public class UnidimensionalSorting extends AbstractConceptTreeBuilder implements ConceptTreeBuilder {

	private IUnidimensionalSorter<IConcept, IIsA> conceptSorter = null;
	private IConcept ontologicalCommitment = null;

	public UnidimensionalSorting() {
	}

	@Override
	public Set<InvertedTree<IConcept, IIsA>> apply(IConceptLattice conceptLattice) {
		InvertedUpperSemilattice<IConcept, IIsA> conceptUSL = conceptLattice.getOntologicalUpperSemilattice();
		try {
			this.conceptSorter = new UnidimensionalSorter<>(conceptUSL);
		} catch (IOException e) {
			System.out.println("ConceptTreeSupplier() : error." + System.lineSeparator() + e.getMessage());
		}
		this.ontologicalCommitment = conceptLattice.getOntologicalCommitment();
		return output();
	}


	private Set<InvertedTree<IConcept, IIsA>> output() {
		Set<InvertedTree<IConcept, IIsA>> commitedTrees = new HashSet<>();
		while (conceptSorter.hasNext())
			commitedTrees.add(AbstractConceptTreeBuilder.commit(conceptSorter.next(), ontologicalCommitment));
		return commitedTrees;
	}

}
