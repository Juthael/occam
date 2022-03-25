package com.tregouet.occam.alg.builders.representations.concept_trees.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.builders.representations.concept_trees.IConceptTreeBuilder;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.tree_finder.algo.unidimensional_sorting.IUnidimensionalSorter;
import com.tregouet.tree_finder.algo.unidimensional_sorting.impl.UnidimensionalSorter;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.data.UpperSemilattice;

public class UnidimensionalSorting extends ConceptTreeBuilder implements IConceptTreeBuilder {

	private IUnidimensionalSorter<IConcept, IIsA> conceptSorter = null;
	private IConcept ontologicalCommitment = null;
	
	public UnidimensionalSorting() {
	}
	
	@Override
	public IConceptTreeBuilder input(IConceptLattice conceptLattice) throws IOException {
		UpperSemilattice<IConcept, IIsA> conceptUSL = conceptLattice.getOntologicalUpperSemilattice();
		try {
			this.conceptSorter = new UnidimensionalSorter<>(conceptUSL);
		} catch (IOException e) {
			throw new IOException("ConceptTreeSupplier() : error." + System.lineSeparator() + e.getMessage());
		}
		this.ontologicalCommitment = conceptLattice.getOntologicalCommitment();
		return this;
	}	
	

	@Override
	public Set<Tree<IConcept, IIsA>> output() {
		Set<Tree<IConcept, IIsA>> commitedTrees = new HashSet<>();
		while (hasNext())
			commitedTrees.add(next());
		return commitedTrees;
	}

	@Override
	public boolean hasNext() {
		return conceptSorter.hasNext();
	}

	@Override
	public Tree<IConcept, IIsA> next() {
		return ConceptTreeBuilder.commit(conceptSorter.next(), ontologicalCommitment);
	}

}
