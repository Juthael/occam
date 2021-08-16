package com.tregouet.occam.transition_function.impl;

import java.util.Iterator;
import java.util.TreeSet;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.transition_function.ICatStructureAwareTFSupplier;
import com.tregouet.occam.transition_function.IIntentAttTreeSupplier;
import com.tregouet.occam.transition_function.IRepresentedCatTree;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.tree_finder.data.InTree;

public class CatStructureAwareTFSupplier extends TransitionFunctionSupplier implements ICatStructureAwareTFSupplier {

	private final TreeSet<IRepresentedCatTree> representedCategories = new TreeSet<>();
	private Iterator<IRepresentedCatTree> ite;
	
	public CatStructureAwareTFSupplier(ICategories categories,
			DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs) {
		super(categories, constructs);
		populateRepresentedCategories();
		ite = representedCategories.iterator();
	}

	@Override
	public ITransitionFunction getOptimalTransitionFunction() {
		return representedCategories.first().getTransitionFunction();
	}

	@Override
	public void reset() {
		ite = representedCategories.iterator();
	}

	@Override
	public boolean hasNext() {
		return ite.hasNext();
	}

	@Override
	public IRepresentedCatTree next() {
		return ite.next();
	}

	@Override
	public InTree<ICategory, DefaultEdge> getOptimalCategoryStructure() {
		return representedCategories.first().getCategoryTree();
	}
	
	private void populateRepresentedCategories() {
		while (categoryTreeSupplier.hasNext()) {
			InTree<ICategory, DefaultEdge> currCatTree = categoryTreeSupplier.next();
			IRepresentedCatTree currCatTreeRepresentation = new RepresentedCatTree(currCatTree);
			DirectedAcyclicGraph<IIntentAttribute, IProduction> filteredConstructGraph = 
					getConstructGraphFilteredByCategoryTree(currCatTree, constructs);
			IIntentAttTreeSupplier attTreeSupplier = new IntentAttTreeSupplier(filteredConstructGraph);
			while (attTreeSupplier.hasNext()) {
				ITransitionFunction transitionFunction = new TransitionFunction(
						categories.getContextObjects(), categories.getObjectCategories(), 
						currCatTree, attTreeSupplier.next());
				currCatTreeRepresentation.testAlternativeRepresentation(transitionFunction);
			}
			if (representedCategories.size() <= MAX_CAPACITY)
				representedCategories.add(currCatTreeRepresentation);
			else if (currCatTreeRepresentation.getCost() < representedCategories.last().getCost()) {
				representedCategories.add(currCatTreeRepresentation);
				representedCategories.pollLast();
			}
		}
	}

}
