package com.tregouet.occam.io.output.impl;

import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IExtentStructureConstraint;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.categories.impl.Categories;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.impl.ProductionBuilder;
import com.tregouet.occam.transition_function.IAttributeConstraint;
import com.tregouet.occam.transition_function.ISimilarityConstraint;
import com.tregouet.occam.transition_function.ITFWithConstrainedExtentStructureSupplier;
import com.tregouet.occam.transition_function.ITFWithConstrainedPropertyStructureSupplier;
import com.tregouet.occam.transition_function.ITFWithConstrainedSimilarityStructureSupplier;
import com.tregouet.occam.transition_function.ITransitionFunctionSupplier;
import com.tregouet.occam.transition_function.impl.TransitionFunctionSupplier;

public class Representations implements com.tregouet.occam.io.output.IRepresentations {

	private final List<IContextObject> objects;
	private final ICategories categories;
	private final DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs = 
			new DirectedAcyclicGraph<>(null, null, false);
	
	public Representations(List<IContextObject> objects) {
		this.objects = objects;
		categories = new Categories(objects);
		List<IProduction> productions = new ProductionBuilder(categories).getProductions();
		productions.stream().forEach(p -> {
			constructs.addVertex(p.getSource());
			constructs.addVertex(p.getTarget());
			constructs.addEdge(p.getSource(), p.getTarget(), p);
		});
	}

	@Override
	public ICategory getLeastCommonCategoryOf(Set<IContextObject> objects) {
		ICategory leastCommonCategory = null;
		List<ICategory> topologicalSorting = categories.getTopologicalSorting();
		ListIterator<ICategory> catIte = topologicalSorting.listIterator(topologicalSorting.size());
		while(catIte.hasPrevious()) {
			ICategory prev = catIte.previous();
			if (prev.getExtent().containsAll(objects))
				leastCommonCategory = prev;
		}
		return leastCommonCategory;
	}

	@Override
	public com.tregouet.occam.io.output.IRepresentations getSubContext(int[] objIndexes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.tregouet.occam.io.output.IRepresentations getSubContext(Set<ICategory> categories) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.tregouet.occam.io.output.IRepresentations getSubContext(List<List<Integer>> objIndexSets) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITransitionFunctionSupplier getRepresentationSupplier() {
		return new TransitionFunctionSupplier(categories, constructs);
	}

	@Override
	public ITFWithConstrainedExtentStructureSupplier getRepresentationSupplier(IExtentStructureConstraint constraint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITFWithConstrainedSimilarityStructureSupplier getRepresentationSupplier(ISimilarityConstraint constraint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITFWithConstrainedPropertyStructureSupplier getRepresentationsSupplier(IAttributeConstraint constraint) {
		// TODO Auto-generated method stub
		return null;
	}

}
