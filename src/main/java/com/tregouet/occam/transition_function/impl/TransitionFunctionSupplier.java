package com.tregouet.occam.transition_function.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.print.attribute.HashAttributeSet;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.cost_calculation.PropertyWeighingStrategy;
import com.tregouet.occam.cost_calculation.SimilarityCalculationStrategy;
import com.tregouet.occam.data.concepts.IClassificationTreeSupplier;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.concepts.impl.ComplementaryConcept;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.io.output.utils.Visualizer;
import com.tregouet.occam.transition_function.ITransitionFunctionSupplier;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.error.InvalidInputException;

public abstract class TransitionFunctionSupplier implements ITransitionFunctionSupplier {

	protected static final int MAX_CAPACITY = 50;
	
	protected final IConcepts concepts;
	protected final IClassificationTreeSupplier categoryTreeSupplier;
	protected final DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs;
	protected final PropertyWeighingStrategy propWeighingStrategy;
	protected final SimilarityCalculationStrategy simCalculationStrategy;
	
	public TransitionFunctionSupplier(IConcepts concepts, 
			DirectedAcyclicGraph<IIntentAttribute, IProduction> constructs, 
			PropertyWeighingStrategy propWeighingStrategy, SimilarityCalculationStrategy simCalculationStrategy) 
					throws InvalidInputException {
		this.concepts = concepts;
		categoryTreeSupplier = concepts.getCatTreeSupplier();
		this.constructs = constructs;
		this.propWeighingStrategy = propWeighingStrategy;
		this.simCalculationStrategy = simCalculationStrategy;
	}

	public static DirectedAcyclicGraph<IIntentAttribute, IProduction> getConstructGraphFilteredByCategoryTree(
			Tree<IConcept, IsA> catTree, DirectedAcyclicGraph<IIntentAttribute, IProduction> unfilteredUnreduced) {
		DirectedAcyclicGraph<IIntentAttribute, IProduction> filtered =	
				new DirectedAcyclicGraph<>(null, null, false);
		List<IProduction> edges = new ArrayList<>();
		List<IProduction> varSwitchers = new ArrayList<>();
		List<IIntentAttribute> varSwitcherSources = new ArrayList<>();
		//HERE
		/*
		List<ICategory> complementary = 
				catTree.vertexSet().stream().filter(c -> c.isComplementary()).collect(Collectors.toList());
		List<ICategory> complemented = new ArrayList<>();
		complementary.stream().forEach(c -> complemented.add(c.getComplemented()));
		boolean test = false;
		for (ICategory cat : complementary) {
			if (!cat.getIntent().isEmpty()) {
				test = true;
				try {
					Visualizer.visualizeCategoryGraph(catTree, "211204_catTree");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}		
		}
		*/
		//HERE
		for (IProduction production : unfilteredUnreduced.edgeSet()) {
			IConcept sourceCat = production.getSourceCategory();
			IConcept targetCat = production.getTargetCategory();
			//HERE
			/*
			if (test && complemented.contains(sourceCat) || complemented.contains(targetCat)) {
				System.out.println("here");
			}
			*/
			//HERE
			if (catTree.containsVertex(sourceCat) 
					&& catTree.containsVertex(targetCat) 
					&& isA(sourceCat, targetCat, catTree)) {
				if (production.isVariableSwitcher()) {
					varSwitchers.add(production);
					varSwitcherSources.add(production.getSource());
				}
				else edges.add(production);
			}
		}
		edges = switchVariables(edges, varSwitchers);
		edges.stream()
			.forEach(e -> {
				filtered.addVertex(e.getSource());
				filtered.addVertex(e.getTarget());
			});
		edges.stream().forEach(p -> filtered.addEdge(p.getSource(), p.getTarget(), p));
		filtered.removeAllVertices(varSwitcherSources);
		//HERE
		/*
		if (test) {
			try {
				Visualizer.visualizeAttributeGraph(filtered, "211204_productions", true);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		*/
		//HERE
		return filtered;
	}
	
	public static List<IProduction> switchVariables(List<IProduction> edges, List<IProduction> varSwitchers){
		List<IProduction> edgesReturned = new ArrayList<>(edges);
		List<IProduction> edgesToRemove = new ArrayList<>();
		List<IProduction> edgesToAdd = new ArrayList<>();
		IProduction newProduction;
		for (IProduction edge : edges) {
			int varSwitcherIdx = 0;
			newProduction = null;
			while (newProduction == null && varSwitcherIdx < varSwitchers.size()) {
				newProduction = edge.switchVariableOrReturnNull(varSwitchers.get(varSwitcherIdx));
				if (newProduction != null) {
					edgesToRemove.add(edge);
					edgesToAdd.add(newProduction);
				}
				varSwitcherIdx++;
			}
		}
		edgesReturned.removeAll(edgesToRemove);
		edgesReturned.addAll(edgesToAdd);
		return edgesReturned;
	}
	
	private static boolean isA(IConcept cat1, IConcept cat2, Tree<IConcept, IsA> tree) {
		return tree.getDescendants(cat1).contains(cat2);
	}

}
