package com.tregouet.occam.data.categories.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.categories.ICatTreeSupplier;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.partitioner.impl.Partitioner;
import com.tregouet.tree_finder.data.InTree;

public class CatTreeSupplier implements ICatTreeSupplier {

	// quasi-lattice : lattice plus ontological commitment as the successor of the maximum
	private final DirectedAcyclicGraph<ICategory, DefaultEdge> categoryLattice;
	private final DirectedAcyclicGraph<ICategory, DefaultEdge> catLattTransitiveReduction;
	private final Set<ICategory> atoms = new HashSet<>();
	private final ICategory ontologicalCommitment;
	private final Map<Set<ICategory>, ICategory> subsetsOfAtomsToTheirSupremums = new HashMap<>();
	private final List<List<Set<ICategory>>> hierarchiesOfAtomSubsets;
	private final Set<Set<ICategory>> treesVertexSets = new HashSet<>();
	private final Iterator<Set<ICategory>> treeVertexSetsIte;
	
	
	/**
	 * Categories' rank must be set.
	 * @param lattice
	 */
	@SuppressWarnings("unchecked")
	public CatTreeSupplier(DirectedAcyclicGraph<ICategory, DefaultEdge> lattice) {
		this.categoryLattice = lattice;
		catLattTransitiveReduction = (DirectedAcyclicGraph<ICategory, DefaultEdge>) lattice.clone();
		TransitiveReduction.INSTANCE.reduce(catLattTransitiveReduction);
		ICategory oC = null;
		for (ICategory category : categoryLattice.vertexSet()) {
			if (category.type() == ICategory.OBJECT)
				atoms.add(category);
			else if (oC == null && category.type() == ICategory.ONTOLOGICAL_COMMITMENT)
				oC = category;
		}
		ontologicalCommitment = oC;
		Set<Set<ICategory>> atomsPowerSet = buildPowerSetOfAtoms();
		for (Set<ICategory> atomSubset : atomsPowerSet) {
			if (!atomSubset.isEmpty()) {
				subsetsOfAtomsToTheirSupremums.put(atomSubset, supremumOf(atomSubset));
			}
		}
		hierarchiesOfAtomSubsets = new Partitioner<ICategory>(atoms).getAllSpanningHierarchies();
		for (List<Set<ICategory>> hierarchy : hierarchiesOfAtomSubsets) {
			Set<ICategory> treeVertexSet = new HashSet<>();
			for (Set<ICategory> subsetOfAtoms : hierarchy) {
				if (subsetOfAtoms.size() == 1)
					treeVertexSet.addAll(subsetOfAtoms);
				else treeVertexSet.add(subsetsOfAtomsToTheirSupremums.get(subsetOfAtoms));
			}
			treeVertexSet.add(ontologicalCommitment);
			treesVertexSets.add(treeVertexSet);
		}
		treeVertexSetsIte = treesVertexSets.iterator();
	}

	@Override
	public int getNbOfTrees() {
		return treesVertexSets.size();
	}

	@Override
	public boolean hasNext() {
		return treeVertexSetsIte.hasNext();
	}

	@Override
	public InTree<ICategory, DefaultEdge> next() {
		Set<ICategory> treeVertexSet = treeVertexSetsIte.next();
		return new InTree<ICategory, DefaultEdge>(categoryLattice, treeVertexSet, ontologicalCommitment, atoms, false);
	}
	
	/*
	 * Parameter should never be empty
	 */
	private ICategory supremumOf(Set<ICategory> categories) {
		if (categories.size() == 1)
			return categories.toArray(new ICategory[1])[0];
		Set<ICategory> commonUpperBounds = null;
		for (ICategory category : categories) {
			if (commonUpperBounds == null) {
				commonUpperBounds = catLattTransitiveReduction.getDescendants(category);
				commonUpperBounds.add(category);
			}
			else {
				Set<ICategory> toBeRetained = catLattTransitiveReduction.getDescendants(category);
				toBeRetained.add(category);
				commonUpperBounds.retainAll(toBeRetained);
			}
		}
		ICategory supremum = null;
		for (ICategory commonUpperBound : commonUpperBounds) {
			if (supremum == null || supremum.rank() > commonUpperBound.rank())
				supremum = commonUpperBound;
		}
		return supremum;
	}
	
	private Set<Set<ICategory>> buildPowerSetOfAtoms() {
		List<ICategory> objects = new ArrayList<>(atoms);
	    Set<Set<ICategory>> powerSet = new HashSet<Set<ICategory>>();
	    for (int i = 0; i < (1 << objects.size()); i++) {
	    	Set<ICategory> subset = new HashSet<ICategory>();
	        for (int j = 0; j < objects.size(); j++) {
	            if(((1 << j) & i) > 0)
	            	subset.add(objects.get(j));
	        }
	        powerSet.add(subset);
	    }
	    return powerSet;
	}

}
