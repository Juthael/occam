package com.tregouet.occam.alg.transition_function_gen.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.transition_function_gen.IConceptStructureBasedTFSupplier;
import com.tregouet.occam.data.abstract_machines.functions.IIsomorphicTransFunctions;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.impl.IsomorphicTransFunctions;
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

public class ConceptStructureBasedTFSupplier extends TransitionFunctionSupplier 
	implements IConceptStructureBasedTFSupplier {

	private final TreeSet<IIsomorphicTransFunctions> transFunctionSets = new TreeSet<>();
	private final Map<IConcept, String> singletonConceptToName = new HashMap<>();
	private Iterator<IIsomorphicTransFunctions> ite;
	
	public ConceptStructureBasedTFSupplier(IConcepts concepts, 
			DirectedAcyclicGraph<IIntentConstruct, IProduction> constructs) throws IOException {
		super(concepts, constructs);
		populateSetsOfRelatedTransFunctions();
		for (IConcept objCat : concepts.getSingletonConcept())
			singletonConceptToName.put(objCat, objCat.getExtent().iterator().next().getName());
		ite = transFunctionSets.iterator();
	}

	@Override
	public String getDefinitionOfObjects() {
		return IConceptStructureBasedTFSupplier.getDefinitionOfObjects(singletonConceptToName);
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
		while (classificationSupplier.hasNext()) {
			Tree<IConcept, IIsA> currClassification = classificationSupplier.next();
			IIsomorphicTransFunctions currSetOfRelatedTransFunctions = new IsomorphicTransFunctions(
					currClassification, singletonConceptToName);
			DirectedAcyclicGraph<IIntentConstruct, IProduction> filteredConstructGraph = 
					getConstructGraphFilteredByCategoryTree(currClassification, constructs);
			IHierarchicalRestrictionFinder<IIntentConstruct, IProduction> constructTreeSupplier = 
					new RestrictorOpt<>(filteredConstructGraph, true);
			while (constructTreeSupplier.hasNext()) {
				Tree<IIntentConstruct, IProduction> constructTree = constructTreeSupplier.nextTransitiveReduction();
				ITransitionFunction transitionFunction = new TransitionFunction(
						currClassification, constructTree);
				if (transitionFunction.validate(TransitionFunctionValidator.INSTANCE))
					currSetOfRelatedTransFunctions.testAlternativeRepresentation(transitionFunction);
			}
			if (currSetOfRelatedTransFunctions.isValid()) {
				if (transFunctionSets.size() <= MAX_CAPACITY)
					transFunctionSets.add(currSetOfRelatedTransFunctions);	
				else if (currSetOfRelatedTransFunctions.getCoherenceScore() 
							> transFunctionSets.last().getCoherenceScore()) {
					transFunctionSets.add(currSetOfRelatedTransFunctions);
					transFunctionSets.pollLast();
				}
			}
		}
	}

}
