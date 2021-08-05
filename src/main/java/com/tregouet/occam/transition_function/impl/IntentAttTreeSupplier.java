package com.tregouet.occam.transition_function.impl;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.transition_function.IIntentAttTreeSupplier;
import com.tregouet.tree_finder.error.InvalidSemiLatticeExeption;
import com.tregouet.tree_finder.impl.TreeFinder;

public class IntentAttTreeSupplier extends TreeFinder<IIntentAttribute, IProduction> implements IIntentAttTreeSupplier {

	public IntentAttTreeSupplier(DirectedAcyclicGraph<IIntentAttribute, IProduction> upperSemiLattice) {
		super(upperSemiLattice);
	}
	
	public IntentAttTreeSupplier(DirectedAcyclicGraph<IIntentAttribute, IProduction> upperSemiLattice, 
			boolean validateArg) throws InvalidSemiLatticeExeption {
		super(upperSemiLattice, validateArg);
	}	

}
