package com.tregouet.occam.transition_function.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.cost_calculation.PropertyWeighingStrategy;
import com.tregouet.occam.cost_calculation.SimilarityCalculationStrategy;
import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.categories.impl.IsA;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.transition_function.ICatStructureAwareTFSupplier;
import com.tregouet.occam.transition_function.IRepresentedCatTree;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.error.InvalidInputException;

public class CatStructureAwareTFSupplier extends TransitionFunctionSupplier implements ICatStructureAwareTFSupplier {

	private final TreeSet<IRepresentedCatTree> representedCategories = new TreeSet<>();
	private final Map<ICategory, String> objectCategoryToName = new HashMap<>();
	private Iterator<IRepresentedCatTree> ite;
	
	public CatStructureAwareTFSupplier(ICategories categories,
			DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs, 
			PropertyWeighingStrategy propWeighingStrategy, SimilarityCalculationStrategy simCalculationStrategy) 
					throws InvalidInputException {
		super(categories, constructs, propWeighingStrategy, simCalculationStrategy);
		populateRepresentedCategories();
		for (ICategory objCat : categories.getObjectCategories())
			objectCategoryToName.put(objCat, objCat.getExtent().iterator().next().getName());
		ite = representedCategories.iterator();
	}

	@Override
	public String getDefinitionOfObjects() {
		return ICatStructureAwareTFSupplier.getDefinitionOfObjects(objectCategoryToName);
	}

	@Override
	public Tree<ICategory, IsA> getOptimalCategoryStructure() {
		return representedCategories.first().getCategoryTree();
	}

	@Override
	public ITransitionFunction getOptimalTransitionFunction() {
		return representedCategories.first().getOptimalTransitionFunction();
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
	public void reset() {
		ite = representedCategories.iterator();
	}	
	
	private void populateRepresentedCategories() {
		while (categoryTreeSupplier.hasNext()) {
			Tree<ICategory, IsA> currCatTree = categoryTreeSupplier.nextOntologicalCommitment();
			IRepresentedCatTree currCatTreeRepresentation = new RepresentedCatTree(currCatTree, objectCategoryToName);
			DirectedAcyclicGraph<IIntentAttribute, IProduction> filteredConstructGraph = 
					getConstructGraphFilteredByCategoryTree(currCatTree, constructs);
			IHierarchicalRestrictionFinder<IIntentAttribute, IProduction> attTreeSupplier = 
					new RestrictorOpt<>(filteredConstructGraph, true);
			while (attTreeSupplier.hasNext()) {
				Tree<IIntentAttribute, IProduction> attTree = attTreeSupplier.nextTransitiveReduction();
				ITransitionFunction transitionFunction = new TransitionFunction(
						categories.getContextObjects(), categories.getObjectCategories(), 
						currCatTree, attTree, propWeighingStrategy, simCalculationStrategy);
				if (transitionFunction.validate(TransitionFunctionValidator.INSTANCE))
					currCatTreeRepresentation.testAlternativeRepresentation(transitionFunction);
			}
			if (currCatTreeRepresentation.isValid()) {
				if (representedCategories.size() <= MAX_CAPACITY)
					representedCategories.add(currCatTreeRepresentation);	
				else if (currCatTreeRepresentation.getCoherenceScore() > representedCategories.last().getCoherenceScore()) {
					representedCategories.add(currCatTreeRepresentation);
					representedCategories.pollLast();
				}
			}

		}
	}

}
