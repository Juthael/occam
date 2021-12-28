package com.tregouet.occam.alg.transition_function_gen.impl;

import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.transition_function_gen.IBasicTFSupplier;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.impl.TransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.utils.TransitionFunctionValidator;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentConstruct;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class BasicTFSupplier extends TransitionFunctionSupplier implements IBasicTFSupplier {

	private final TreeSet<ITransitionFunction> transitionFunctions = new TreeSet<>(functionComparator);
	private Iterator<ITransitionFunction> ite;
	
	public BasicTFSupplier(IConcepts concepts, DirectedAcyclicGraph<IIntentConstruct, IProduction> constructs) 
			throws IOException {
		super(concepts, constructs);
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
		while (classificationSupplier.hasNext()) {
			Tree<IConcept, IIsA> currClassification = classificationSupplier.next();
			DirectedAcyclicGraph<IIntentConstruct, IProduction> filteredConstructGraph = 
					getConstructGraphFilteredByCategoryTree(currClassification, constructs);
			IHierarchicalRestrictionFinder<IIntentConstruct, IProduction> constructTreeSupplier = 
					new RestrictorOpt<IIntentConstruct, IProduction>(filteredConstructGraph, true);
			while (constructTreeSupplier.hasNext()) {
				Tree<IIntentConstruct, IProduction> constructTree = constructTreeSupplier.nextTransitiveReduction();
				ITransitionFunction transitionFunction = new TransitionFunction(currClassification, constructTree);
				if (transitionFunction.validate(TransitionFunctionValidator.INSTANCE)) {
					transitionFunctions.add(transitionFunction);
					if (transitionFunctions.size() > MAX_CAPACITY)
						transitionFunctions.remove(transitionFunctions.first());
				}
			}
		}
	}	
}
