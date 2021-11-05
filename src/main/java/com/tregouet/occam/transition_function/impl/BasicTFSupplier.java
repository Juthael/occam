package com.tregouet.occam.transition_function.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.categories.ICategories;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.impl.Production;
import com.tregouet.occam.io.output.utils.Visualizer;
import com.tregouet.occam.transition_function.IBasicTFSupplier;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.tree_finder.ITreeFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.IHierarchicalRestrictionFinder;
import com.tregouet.tree_finder.algo.hierarchical_restriction.impl.RestrictorOpt;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.error.InvalidInputException;

public class BasicTFSupplier extends TransitionFunctionSupplier implements IBasicTFSupplier {

	private final TreeSet<ITransitionFunction> transitionFunctions = new TreeSet<>();
	private Iterator<ITransitionFunction> ite;
	
	public BasicTFSupplier(ICategories categories, DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs) 
			throws InvalidInputException {
		super(categories, constructs);
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
		//HERE
		
		int catTreeIdx = 0;
		int tfIdx = 0;
		try {
			Visualizer.visualizeCategoryGraph(categories.getTransitiveReduction(), "catLattice");
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		//HERE
		while (categoryTreeSupplier.hasNext()) {
			Tree<ICategory, DefaultEdge> currCatTree = categoryTreeSupplier.nextOntologicalCommitment();
			
			try {
				Visualizer.visualizeCategoryGraph(currCatTree, "current_cat_tree" + Integer.toString(catTreeIdx++));
				tfIdx = 0;
			} catch (IOException e1) {
				System.out.println("failed0");
			}
			
			Map<Integer, Set<Integer>> objCatIDToSuperCatsInCatTree = new HashMap<>();
			for (ICategory objCat : currCatTree.getLeaves()) {
				Set<Integer> objCatSuperCatsIDs = new HashSet<>();
				for (ICategory objCatSuperCat : currCatTree.getDescendants(objCat))
					objCatSuperCatsIDs.add((Integer) objCatSuperCat.getID());
				objCatIDToSuperCatsInCatTree.put((Integer) objCat.getID(), objCatSuperCatsIDs);
			}
			DirectedAcyclicGraph<IIntentAttribute, IProduction> filteredConstructGraph = 
					getConstructGraphFilteredByCategoryTree(currCatTree, constructs);
			/*
			try {
				Visualizer.visualizeAttributeGraph(filteredConstructGraph, "filtered_constructs_graph");
			} catch (IOException e) {
				System.out.println("failed1");
			}
			*/
			IHierarchicalRestrictionFinder<IIntentAttribute, IProduction> attTreeSupplier = 
					new RestrictorOpt<IIntentAttribute, IProduction>(filteredConstructGraph, true);
			while (attTreeSupplier.hasNext()) {
				Tree<IIntentAttribute, IProduction> attTree = attTreeSupplier.next();
				/*
				try {
					Visualizer.visualizeAttributeGraph(attTree, "tree_of_attributes");
				} catch (IOException e) {
					System.out.println("failed2");
				}
				*/
				ITransitionFunction transitionFunction = new TransitionFunction(
						categories.getContextObjects(), categories.getObjectCategories(), 
						currCatTree, attTree);
				//HERE
				/*
				try {
					Visualizer.visualizeTransitionFunction(transitionFunction, "transitionFunction"
							+ Integer.toString(catTreeIdx - 1) + "-" + Integer.toString(tfIdx++), true);
				} catch (IOException e) {
					System.out.println("failed3");
				}
				*/
				//HERE
				if (transitionFunctions.size() <= MAX_CAPACITY)
					transitionFunctions.add(transitionFunction);
				else if (transitionFunction.getCoherenceScore() > transitionFunctions.last().getCoherenceScore()) {
					transitionFunctions.add(transitionFunction);
					transitionFunctions.pollLast();
				}
			}
		}
		//HERE
		//System.out.println("over");
		//HERE
	}	

}
