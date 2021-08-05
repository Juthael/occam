package com.tregouet.occam.transition_function.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.categories.ICatTreeSupplier;
import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.transition_function.IIntentAttTreeSupplier;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.occam.transition_function.ITransitionFunctionSupplier;
import com.tregouet.tree_finder.data.InTree;

public class TransitionFunctionSupplier implements ITransitionFunctionSupplier {

	private static final int MAX_CAPACITY = 50;
	
	private final ICategories categories;
	private final ICatTreeSupplier categoryTreeSupplier;
	private final DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs;
	private final TreeSet<ITransitionFunction> transitionFunctions = new TreeSet<>();
	private Iterator<ITransitionFunction> ite = transitionFunctions.iterator();
	
	public TransitionFunctionSupplier(ICategories categories, 
			DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs) {
		this.categories = categories;
		categoryTreeSupplier = categories.getCatTreeSupplier();
		this.constructs = constructs;
		buildTransitionFunctions();
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
	public ITransitionFunction getOptimalTransitionFunction() {
		return transitionFunctions.first();
	}

	@Override
	public void reset() {
		ite = transitionFunctions.iterator();
	}
	
	private void buildTransitionFunctions() {
		while (categoryTreeSupplier.hasNext()) {
			InTree<ICategory, DefaultEdge> currCatTree = categoryTreeSupplier.next();
			DirectedAcyclicGraph<IIntentAttribute, IProduction> filteredConstructGraph = 
					getConstructGraphFilteredByCategoryTree(currCatTree, constructs);
			IIntentAttTreeSupplier constrTreeSupplier = new IntentAttTreeSupplier(filteredConstructGraph);
			while (constrTreeSupplier.hasNext()) {
				ITransitionFunction transitionFunction = new TransitionFunction(
						categories.getContextObjects(), categories.getObjectCategories(), 
						currCatTree, constrTreeSupplier.next());
				if (transitionFunctions.size() <= MAX_CAPACITY)
					transitionFunctions.add(transitionFunction);
				else if (transitionFunction.getCost() < transitionFunctions.last().getCost()) {
					transitionFunctions.add(transitionFunction);
					transitionFunctions.pollLast();
				}
			}
		}
	}
	
	public static DirectedAcyclicGraph<IIntentAttribute, IProduction> getConstructGraphFilteredByCategoryTree(
			InTree<ICategory, DefaultEdge> catTree, DirectedAcyclicGraph<IIntentAttribute, IProduction> unfiltered) {
		DirectedAcyclicGraph<IIntentAttribute, IProduction> filtered =	
				new DirectedAcyclicGraph<>(null, null, false);
		Set<IIntentAttribute> vertices = new HashSet<>();
		List<IProduction> edges = new ArrayList<>();
		for (IProduction production : unfiltered.edgeSet()) {
			if (isA(production.getSourceCategory(), production.getTargetCategory(), catTree)) {
				vertices.add(production.getSource());
				vertices.add(production.getTarget());
				edges.add(production);
			}
		}
		vertices.stream().forEach(i -> filtered.addVertex(i));
		edges.stream().forEach(p -> filtered.addEdge(p.getSource(), p.getTarget(), p));
		return filtered;
	}
	
	private static boolean isA(ICategory cat1, ICategory cat2, InTree<ICategory, DefaultEdge> tree) {
		return tree.getDescendants(cat1).contains(cat2);
	}

}
