package com.tregouet.occam.transition_function.impl;

import java.util.Iterator;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.cost_calculation.PropertyWeighingStrategy;
import com.tregouet.occam.cost_calculation.SimilarityCalculationStrategy;
import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.categories.impl.IsA;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.transition_function.IBasicTFSupplier;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.error.InvalidInputException;

public class BasicTFSupplier extends TransitionFunctionSupplier implements IBasicTFSupplier {

	private final TreeSet<ITransitionFunction> transitionFunctions = new TreeSet<>();
	private Iterator<ITransitionFunction> ite;
	
	public BasicTFSupplier(ICategories categories, DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs, 
			PropertyWeighingStrategy propWeighingStrategy, SimilarityCalculationStrategy simCalculationStrategy) 
			throws InvalidInputException {
		super(categories, constructs, propWeighingStrategy, simCalculationStrategy);
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
			Tree<ICategory, IsA> currCatTree = categoryTreeSupplier.nextOntologicalCommitment();
			DirectedAcyclicGraph<IIntentAttribute, IProduction> filteredConstructGraph = 
					getConstructGraphFilteredByCategoryTree(currCatTree, constructs);
			IHierarchicalRestrictionFinder<IIntentAttribute, IProduction> attTreeSupplier = 
					new RestrictorOpt<IIntentAttribute, IProduction>(filteredConstructGraph, true);
			while (attTreeSupplier.hasNext()) {
				Tree<IIntentAttribute, IProduction> attTree = attTreeSupplier.nextTransitiveReduction();
				ITransitionFunction transitionFunction = new TransitionFunction(
						categories.getContextObjects(), categories.getObjectCategories(), 
						currCatTree, attTree, propWeighingStrategy, simCalculationStrategy);
				if (transitionFunction.validate(TransitionFunctionValidator.INSTANCE)) {
					if (transitionFunctions.size() <= MAX_CAPACITY)
						transitionFunctions.add(transitionFunction);
					else if (transitionFunction.getCoherenceScore() > transitionFunctions.last().getCoherenceScore()) {
						transitionFunctions.add(transitionFunction);
						transitionFunctions.pollLast();
					}
				}
			}
		}
	}	

}
