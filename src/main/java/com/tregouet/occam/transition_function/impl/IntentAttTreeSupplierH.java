package com.tregouet.occam.transition_function.impl;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.transition_function.IIntentAttTreeSupplier;
import com.tregouet.tree_finder.data.InTree;

public class IntentAttTreeSupplierH implements IIntentAttTreeSupplier {

	public IntentAttTreeSupplierH(DirectedAcyclicGraph<IIntentAttribute, IProduction> upperSemiLattice) {
		// TODO Auto-generated constructor stub
	}
	
	public IntentAttTreeSupplierH(DirectedAcyclicGraph<IIntentAttribute, IProduction> upperSemiLattice, 
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
