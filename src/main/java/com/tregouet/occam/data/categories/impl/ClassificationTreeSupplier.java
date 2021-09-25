package com.tregouet.occam.data.categories.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IClassificationTreeSupplier;
import com.tregouet.partitioner.impl.ConstrainedPartitioner;
import com.tregouet.partitioner.impl.Partitioner;
import com.tregouet.tree_finder.data.InTree;

public class ClassificationTreeSupplier implements IClassificationTreeSupplier {

	// quasi-lattice : lattice plus 'ontological commitment' as the successor of the maximum
	private final DirectedAcyclicGraph<ICategory, DefaultEdge> categoryLattice;
	private final DirectedAcyclicGraph<ICategory, DefaultEdge> catLattTransitiveReduction;
	private final Map<Integer, ICategory> idToCategory = new HashMap<>();
	private final Set<ICategory> atoms = new HashSet<>();
	private final Set<Integer> atomIDs = new HashSet<>();
	private final Map<Set<Integer>, Integer> subsetsOfAtomIDsToTheirSupremumIDs = new HashMap<>();
	private final ICategory ontologicalCommitment;
	private final List<List<Set<Integer>>> encodingHierarchiesOfAtomIDsSubsets;
	private final Set<Set<ICategory>> classificationTreesVertexSets = new HashSet<>();
	private final Iterator<Set<ICategory>> classificationTreesVertexSetsIte;
	
	
	/**
	 * Categories' rank must be set.
	 * @param lattice
	 */
	@SuppressWarnings("unchecked")
	public ClassificationTreeSupplier(DirectedAcyclicGraph<ICategory, DefaultEdge> lattice) {
		this.categoryLattice = lattice;
		catLattTransitiveReduction = (DirectedAcyclicGraph<ICategory, DefaultEdge>) lattice.clone();
		TransitiveReduction.INSTANCE.reduce(catLattTransitiveReduction);
		ICategory oC = null;
		for (ICategory category : categoryLattice.vertexSet()) {
			idToCategory.put(category.getID(), category);
			if (category.type() == ICategory.OBJECT) {
				atoms.add(category);
				atomIDs.add(category.getID());
			}	
			else if (oC == null && category.type() == ICategory.ONTOLOGICAL_COMMITMENT)
				oC = category;
		}
		ontologicalCommitment = oC;
		encodeLatticeInThePowerSetOfItsAtoms();
		Set<Set<Integer>> maximalEncodingSubsetsOfAtoms = new HashSet<>(subsetsOfAtomIDsToTheirSupremumIDs.keySet());
		encodingHierarchiesOfAtomIDsSubsets = 
				new ConstrainedPartitioner<Integer>(atomIDs, maximalEncodingSubsetsOfAtoms).getAllSpanningHierarchies();
		for (List<Set<Integer>> hierarchyOfAtomIDs : encodingHierarchiesOfAtomIDsSubsets) {
			Set<ICategory> treeVertexSet = new HashSet<>();
			for (Set<Integer> subsetOfAtomIDs : hierarchyOfAtomIDs) {
				treeVertexSet.add(idToCategory.get(subsetsOfAtomIDsToTheirSupremumIDs.get(subsetOfAtomIDs)));
				treeVertexSet.add(ontologicalCommitment);
			}
			classificationTreesVertexSets.add(treeVertexSet);
		}
		classificationTreesVertexSetsIte = classificationTreesVertexSets.iterator();
	}

	@Override
	public int getNbOfTrees() {
		return classificationTreesVertexSets.size();
	}

	@Override
	public boolean hasNext() {
		return classificationTreesVertexSetsIte.hasNext();
	}

	@Override
	public InTree<ICategory, DefaultEdge> next() {
		Set<ICategory> treeVertexSet = classificationTreesVertexSetsIte.next();
		return new InTree<ICategory, DefaultEdge>(categoryLattice, treeVertexSet, ontologicalCommitment, atoms, false);
	}
	
	private void encodeLatticeInThePowerSetOfItsAtoms() {
		for (ICategory category : categoryLattice.vertexSet()) {
			int categoryType = category.type();
			if (categoryType != ICategory.ABSURDITY && categoryType != ICategory.ONTOLOGICAL_COMMITMENT) {
				Set<Integer> atomSubSetIDs = getLowerBoundsIDs(categoryLattice, category);
				atomSubSetIDs.retainAll(atomIDs);
				subsetsOfAtomIDsToTheirSupremumIDs.put(atomSubSetIDs, category.getID());
			}
		}
	}
	
	private static Set<ICategory> getLowerBounds(DirectedAcyclicGraph<ICategory, DefaultEdge> categoryLattice, 
			ICategory category) {
		Set<ICategory> lowerBounds = categoryLattice.getAncestors(category);
		lowerBounds.add(category);
		return lowerBounds;
	}
	
	private static Set<Integer> getLowerBoundsIDs(DirectedAcyclicGraph<ICategory, DefaultEdge> categoryLattice, 
			ICategory category) {
		Set<ICategory> lowerBounds = getLowerBounds(categoryLattice, category);
		Set<Integer> lowerBoundsIDs = new HashSet<>();
		for (ICategory lowerBound : lowerBounds)
			lowerBoundsIDs.add(lowerBound.getID());
		return lowerBoundsIDs;
	}

}
