package com.tregouet.occam.transition_function.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.transition_function.IIntentAttTreeSupplier;
import com.tregouet.tree_finder.data.InTree;

public class IntentAttTreeSupplierH implements IIntentAttTreeSupplier {

	private final DirectedAcyclicGraph<IIntentAttribute, IProduction> reducedUpperSemiLattice;
	private final Map<Integer, IIntentAttribute> idToAttribute = new HashMap<>();
	private final Set<IIntentAttribute> atoms = new HashSet<>();
	private final Set<Integer> atomIDs = new HashSet<>();
	private final Map<Set<Integer>, Integer> subsetsOfAtomIDsToTheirSupremumIDs = new HashMap<>();
	private final IIntentAttribute initialVariable;
	private final List<List<Set<Integer>>> encodingHierarchiesOfAtomIDsSubsets;
	private final Set<Set<IIntentAttribute>> classificationTreesVertexSets = new HashSet<>();
	private final Iterator<Set<ICategory>> classificationTreesVertexSetsIte;
	
	public IntentAttTreeSupplierH(DirectedAcyclicGraph<IIntentAttribute, IProduction> reducedUpperSemiLattice) {
		// TODO Auto-generated constructor stub
	}
	
	public IntentAttTreeSupplierH(DirectedAcyclicGraph<IIntentAttribute, IProduction> reducedUpperSemiLattice, 
			boolean validateArg) {
		// TODO Auto-generated constructor stub
	}	

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public InTree<IIntentAttribute, IProduction> next() {
		// TODO Auto-generated method stub
		return null;
	}

}
