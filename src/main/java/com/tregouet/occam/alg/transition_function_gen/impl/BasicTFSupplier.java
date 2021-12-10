package com.tregouet.occam.alg.transition_function_gen.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.cost_calc.SimilarityCalculationStrategy;
import com.tregouet.occam.alg.transition_function_gen.IBasicTFSupplier;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.impl.TransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.utils.TransitionFunctionValidator;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class BasicTFSupplier extends TransitionFunctionSupplier implements IBasicTFSupplier {

	private final TreeSet<ITransitionFunction> transitionFunctions = new TreeSet<>();
	private Iterator<ITransitionFunction> ite;
	
	public BasicTFSupplier(IConcepts concepts, DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs, 
			SimilarityCalculationStrategy simCalculationStrategy) throws IOException {
		super(concepts, constructs, simCalculationStrategy);
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
			Tree<IConcept, IsA> currCatTree = categoryTreeSupplier.nextOntologicalCommitment();
			DirectedAcyclicGraph<IIntentAttribute, IProduction> filteredConstructGraph = 
					getConstructGraphFilteredByCategoryTree(currCatTree, constructs);
			IHierarchicalRestrictionFinder<IIntentAttribute, IProduction> attTreeSupplier = 
					new RestrictorOpt<IIntentAttribute, IProduction>(filteredConstructGraph, true);
			while (attTreeSupplier.hasNext()) {
				Tree<IIntentAttribute, IProduction> attTree = attTreeSupplier.nextTransitiveReduction();
				ITransitionFunction transitionFunction = new TransitionFunction(
						concepts.getContextObjects(), concepts.getSingletonConcept(), 
						currCatTree, attTree, simCalculationStrategy);
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
