package com.tregouet.occam.alg.builders.preconcepts.trees.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.builders.preconcepts.trees.IPreconceptTreeBuilder;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.preconcepts.IPreconceptLattice;
import com.tregouet.tree_finder.algo.unidimensional_sorting.IUnidimensionalSorter;
import com.tregouet.tree_finder.algo.unidimensional_sorting.impl.UnidimensionalSorter;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.data.UpperSemilattice;

public class UnidimensionalSorting extends PreconceptTreeBuilder implements IPreconceptTreeBuilder {

	private IUnidimensionalSorter<IPreconcept, IIsA> preconceptSorter = null;
	private IPreconcept ontologicalCommitment = null;
	
	public UnidimensionalSorting() {
	}
	
	@Override
	public IPreconceptTreeBuilder input(IPreconceptLattice preconceptLattice) throws IOException {
		UpperSemilattice<IPreconcept, IIsA> conceptUSL = preconceptLattice.getOntologicalUpperSemilattice();
		try {
			this.preconceptSorter = new UnidimensionalSorter<>(conceptUSL);
		} catch (IOException e) {
			throw new IOException("ConceptTreeSupplier() : error." + System.lineSeparator() + e.getMessage());
		}
		this.ontologicalCommitment = preconceptLattice.getOntologicalCommitment();
		return this;
	}	
	

	@Override
	public Set<Tree<IPreconcept, IIsA>> output() {
		Set<Tree<IPreconcept, IIsA>> commitedTrees = new HashSet<>();
		while (hasNext())
			commitedTrees.add(next());
		return commitedTrees;
	}

	@Override
	public boolean hasNext() {
		return preconceptSorter.hasNext();
	}

	@Override
	public Tree<IPreconcept, IIsA> next() {
		return PreconceptTreeBuilder.commit(preconceptSorter.next(), ontologicalCommitment);
	}

}
