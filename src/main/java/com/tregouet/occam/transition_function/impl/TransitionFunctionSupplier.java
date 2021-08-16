package com.tregouet.occam.transition_function.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.categories.ICatTreeSupplier;
import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.transition_function.ITransitionFunctionSupplier;
import com.tregouet.tree_finder.data.InTree;

public abstract class TransitionFunctionSupplier implements ITransitionFunctionSupplier {

	protected static final int MAX_CAPACITY = 50;
	
	protected final ICategories categories;
	protected final ICatTreeSupplier categoryTreeSupplier;
	protected final DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs;
	
	public TransitionFunctionSupplier(ICategories categories, 
			DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs) {
		this.categories = categories;
		categoryTreeSupplier = categories.getCatTreeSupplier();
		this.constructs = constructs;
		
	}

	public static DirectedAcyclicGraph<IIntentAttribute, IProduction> getConstructGraphFilteredByCategoryTree(
			InTree<ICategory, DefaultEdge> catTree, DirectedAcyclicGraph<IIntentAttribute, IProduction> unfiltered) {
		DirectedAcyclicGraph<IIntentAttribute, IProduction> filtered =	
				new DirectedAcyclicGraph<>(null, null, false);
		Set<IIntentAttribute> vertices = new HashSet<>();
		List<IProduction> edges = new ArrayList<>();
		for (IProduction production : unfiltered.edgeSet()) {
			ICategory sourceCat = production.getSourceCategory();
			ICategory targetCat = production.getTargetCategory();
			if (catTree.containsVertex(sourceCat) && catTree.containsVertex(targetCat) && isA(sourceCat, targetCat, catTree)) {
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
