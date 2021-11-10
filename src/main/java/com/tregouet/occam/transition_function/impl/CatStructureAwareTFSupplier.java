package com.tregouet.occam.transition_function.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

	private static int iterationsOverAlphabet = 0;
	private static Iterator<Character> charIte = populateCharList().iterator();
	
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
			objectCategoryToName.put(objCat, provideName());
		ite = representedCategories.iterator();
	}

	private static List<Character> populateCharList(){
		List<Character> authorizedCharASCII = new ArrayList<Character>();
		for (char curr = 'A' ; curr <= 'Z' ; curr++) {
			authorizedCharASCII.add(curr);
		}
		for (char curr = 945 ; curr <= 965 ; curr++) {
			authorizedCharASCII.add(curr);
		}
		return authorizedCharASCII;
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
		return representedCategories.first().getTransitionFunction();
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
	
	private char getNextChar() {
		if (!charIte.hasNext()) {
			charIte = populateCharList().iterator();
			iterationsOverAlphabet++;
		}
		return charIte.next();
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
				currCatTreeRepresentation.testAlternativeRepresentation(transitionFunction);
			}
			if (representedCategories.size() <= MAX_CAPACITY)
				representedCategories.add(currCatTreeRepresentation);
			else if (currCatTreeRepresentation.getCoherenceScore() > representedCategories.last().getCoherenceScore()) {
				representedCategories.add(currCatTreeRepresentation);
				representedCategories.pollLast();
			}
		}
	}
	
	private String provideName() {
		StringBuffer sB = new StringBuffer();
		sB.append(getNextChar());
		for (int i = 0 ; i < iterationsOverAlphabet ; i++) {
			sB.append("'");
		}
		return sB.toString();
	}	

}
