package com.tregouet.occam.alg.transition_function_gen.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.cost_calc.SimilarityCalculationStrategy;
import com.tregouet.occam.alg.transition_function_gen.IConceptStructureBasedTFSupplier;
import com.tregouet.occam.data.abstract_machines.functions.IRelatedTransFunctions;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.impl.RelatedTransFunctions;
import com.tregouet.occam.data.abstract_machines.functions.impl.TransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.util.TransitionFunctionValidator;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;

public class ConceptStructureBasedTFSupplier extends TransitionFunctionSupplier implements IConceptStructureBasedTFSupplier {

	private final TreeSet<IRelatedTransFunctions> representedCategories = new TreeSet<>();
	private final Map<IConcept, String> objectCategoryToName = new HashMap<>();
	private Iterator<IRelatedTransFunctions> ite;
	
	public ConceptStructureBasedTFSupplier(IConcepts concepts, 
			DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs, 
			SimilarityCalculationStrategy simCalculationStrategy) throws IOException {
		super(concepts, constructs, simCalculationStrategy);
		populateRepresentedCategories();
		for (IConcept objCat : concepts.getSingletonConcept())
			objectCategoryToName.put(objCat, objCat.getExtent().iterator().next().getName());
		ite = representedCategories.iterator();
	}

	@Override
	public String getDefinitionOfObjects() {
		return IConceptStructureBasedTFSupplier.getDefinitionOfObjects(objectCategoryToName);
	}

	@Override
	public Tree<IConcept, IsA> getOptimalCategoryStructure() {
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
	public IRelatedTransFunctions next() {
		return ite.next();
	}
	
	@Override
	public void reset() {
		ite = representedCategories.iterator();
	}	
	
	private void populateRepresentedCategories() {
		while (categoryTreeSupplier.hasNext()) {
			Tree<IConcept, IsA> currCatTree = categoryTreeSupplier.nextOntologicalCommitment();
			IRelatedTransFunctions currCatTreeRepresentation = new RelatedTransFunctions(currCatTree, objectCategoryToName);
			DirectedAcyclicGraph<IIntentAttribute, IProduction> filteredConstructGraph = 
					getConstructGraphFilteredByCategoryTree(currCatTree, constructs);
			IHierarchicalRestrictionFinder<IIntentAttribute, IProduction> attTreeSupplier = 
					new RestrictorOpt<>(filteredConstructGraph, true);
			while (attTreeSupplier.hasNext()) {
				Tree<IIntentAttribute, IProduction> attTree = attTreeSupplier.nextTransitiveReduction();
				ITransitionFunction transitionFunction = new TransitionFunction(
						concepts.getContextObjects(), concepts.getSingletonConcept(), 
						currCatTree, attTree, simCalculationStrategy);
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
