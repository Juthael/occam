package com.tregouet.occam.transition_function.impl;

import java.util.Iterator;
import java.util.TreeSet;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.transition_function.IBasicTFSupplier;
import com.tregouet.occam.transition_function.IIntentAttTreeSupplier;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.tree_finder.data.InTree;

public class BasicTFSupplier extends TransitionFunctionSupplier implements IBasicTFSupplier {

	private final TreeSet<ITransitionFunction> transitionFunctions = new TreeSet<>();
	private Iterator<ITransitionFunction> ite;
	
	public BasicTFSupplier(ICategories categories, DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs) {
		super(categories, constructs);
		populateTransitionFunctions();
		ite = transitionFunctions.iterator();
	}
	
	@Override
	public ITransitionFunction getOptimalTransitionFunction() {
		return transitionFunctions.first();
	}
	
	@Override
	public boolean hasNext() {
		return ite.hasNext();
	}

	@Override
	public ITransitionFunction next() {
		return ite.next();
	}

	@Override
	public void reset() {
		ite = transitionFunctions.iterator();
	}

	private void populateTransitionFunctions() {
		while (categoryTreeSupplier.hasNext()) {
			InTree<ICategory, DefaultEdge> currCatTree = categoryTreeSupplier.nextWithTunnelCategoriesRemoved();
			DirectedAcyclicGraph<IIntentAttribute, IProduction> filteredConstructGraph = 
					getConstructGraphFilteredByCategoryTree(currCatTree, constructs);
			IIntentAttTreeSupplier attTreeSupplier = new IntentAttTreeSupplier(filteredConstructGraph);
			while (attTreeSupplier.hasNext()) {
				ITransitionFunction transitionFunction = new TransitionFunction(
						categories.getContextObjects(), categories.getObjectCategories(), 
						currCatTree, attTreeSupplier.next());
				if (transitionFunctions.size() <= MAX_CAPACITY)
					transitionFunctions.add(transitionFunction);
				else if (transitionFunction.getCoherenceScore() < transitionFunctions.last().getCoherenceScore()) {
					transitionFunctions.add(transitionFunction);
					transitionFunctions.pollLast();
				}
			}
		}
	}	

}
