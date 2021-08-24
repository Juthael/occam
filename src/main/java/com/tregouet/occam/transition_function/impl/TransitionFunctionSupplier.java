package com.tregouet.occam.transition_function.impl;

import java.util.ArrayList;
import java.util.List;

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
			InTree<ICategory, DefaultEdge> catTree, 
			DirectedAcyclicGraph<IIntentAttribute, IProduction> unfiltered) {
		DirectedAcyclicGraph<IIntentAttribute, IProduction> filtered =	
				new DirectedAcyclicGraph<>(null, null, false);
		List<IProduction> edges = new ArrayList<>();
		List<IProduction> varSwitchers = new ArrayList<>();
		for (IProduction production : unfiltered.edgeSet()) {
			ICategory sourceCat = production.getSourceCategory();
			ICategory targetCat = production.getTargetCategory();
			if (catTree.containsVertex(sourceCat) 
					&& catTree.containsVertex(targetCat) 
					&& isA(sourceCat, targetCat, catTree)) {
				if (production.isVariableSwitcher())
					varSwitchers.add(production);
				else edges.add(production);
			}
		}
		edges = switchVariables(edges, varSwitchers);
		edges.stream()
			.forEach(e -> {
				if (e.getSourceCategory().type() == ICategory.OBJECT)
					filtered.addVertex(e.getSource());
				filtered.addVertex(e.getTarget());
			});
		edges.stream().forEach(p -> filtered.addEdge(p.getSource(), p.getTarget(), p));
		return filtered;
	}
	
	private static boolean isA(ICategory cat1, ICategory cat2, InTree<ICategory, DefaultEdge> tree) {
		return tree.getDescendants(cat1).contains(cat2);
	}
	
	public static List<IProduction> switchVariables(List<IProduction> edges, List<IProduction> varSwitchers){
		List<IProduction> edgesReturned = new ArrayList<>(edges);
		List<IProduction> edgesToRemove = new ArrayList<>();
		List<IProduction> edgesToAdd = new ArrayList<>();
		IProduction newProduction;
		for (IProduction edge : edges) {
			int varSwitcherIdx = 0;
			newProduction = null;
			while (newProduction == null && varSwitcherIdx < varSwitchers.size()) {
				newProduction = edge.switchVariableOrReturnNull(varSwitchers.get(varSwitcherIdx));
				if (newProduction != null) {
					edgesToRemove.add(edge);
					edgesToAdd.add(newProduction);
				}
				varSwitcherIdx++;
			}
		}
		edgesReturned.removeAll(edgesToRemove);
		edgesReturned.addAll(edgesToAdd);
		return edgesReturned;
	}
	


}
