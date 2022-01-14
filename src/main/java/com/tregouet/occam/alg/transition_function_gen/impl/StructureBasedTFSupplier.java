package com.tregouet.occam.alg.transition_function_gen.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.transition_function_gen.IStructureBasedTFSupplier;
import com.tregouet.occam.data.abstract_machines.functions.IIsomorphicTransFunctions;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.impl.IsomorphicTransFunctions;
import com.tregouet.occam.data.abstract_machines.functions.impl.TransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.utils.TransitionFunctionValidator;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotationSets;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.languages.specific.IProduction;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class StructureBasedTFSupplier extends TransitionFunctionSupplier 
	implements IStructureBasedTFSupplier {

	private final TreeSet<IIsomorphicTransFunctions> transFunctionSets = new TreeSet<>();
	private final Map<IDenotationSet, String> objectDenotationSetToName = new HashMap<>();
	private Iterator<IIsomorphicTransFunctions> ite;
	
	public StructureBasedTFSupplier(IDenotationSets denotationSets, 
			DirectedAcyclicGraph<IDenotation, IProduction> denotations) throws IOException {
		super(denotationSets, denotations);
		populateSetsOfRelatedTransFunctions();
		for (IDenotationSet objDenotationSet : denotationSets.getObjectDenotationSets())
			objectDenotationSetToName.put(objDenotationSet, objDenotationSet.getExtent().iterator().next().getName());
		ite = transFunctionSets.iterator();
	}

	@Override
	public String getObjectDenotationsAsString() {
		return IStructureBasedTFSupplier.getObjectsDenotationsAsString(objectDenotationSetToName);
	}

	@Override
	public ITransitionFunction getOptimalTransitionFunction() {
		return transFunctionSets.first().getOptimalTransitionFunction();
	}

	@Override
	public boolean hasNext() {
		return ite.hasNext();
	}
	
	@Override
	public IIsomorphicTransFunctions next() {
		return ite.next();
	}
	
	@Override
	public void reset() {
		ite = transFunctionSets.iterator();
	}	
	
	private void populateSetsOfRelatedTransFunctions() {
		while (denotationSetsTreeSupplier.hasNext()) {
			Tree<IDenotationSet, IIsA> currTreeOfDenotationSets = denotationSetsTreeSupplier.next();
			IIsomorphicTransFunctions currSetOfIsomorphicTransFunctions = new IsomorphicTransFunctions(
					currTreeOfDenotationSets, objectDenotationSetToName);
			DirectedAcyclicGraph<IDenotation, IProduction> filteredDenotationGraph = 
					getDenotationGraphFilteredByTreeOfDenotationSets(currTreeOfDenotationSets, denotations);
			IHierarchicalRestrictionFinder<IDenotation, IProduction> denotationTreeSupplier = 
					new RestrictorOpt<>(filteredDenotationGraph, true);
			while (denotationTreeSupplier.hasNext()) {
				Tree<IDenotation, IProduction> denotationTree = denotationTreeSupplier.nextTransitiveReduction();
				ITransitionFunction transitionFunction = new TransitionFunction(
						currTreeOfDenotationSets, denotationTree);
				if (transitionFunction.validate(TransitionFunctionValidator.INSTANCE))
					currSetOfIsomorphicTransFunctions.testAlternativeRepresentation(transitionFunction);
			}
			if (currSetOfIsomorphicTransFunctions.isValid()) {
				if (transFunctionSets.size() <= MAX_CAPACITY)
					transFunctionSets.add(currSetOfIsomorphicTransFunctions);	
				else if (currSetOfIsomorphicTransFunctions.getCoherenceScore() 
							> transFunctionSets.last().getCoherenceScore()) {
					transFunctionSets.add(currSetOfIsomorphicTransFunctions);
					transFunctionSets.pollLast();
				}
			}
		}
	}

}
