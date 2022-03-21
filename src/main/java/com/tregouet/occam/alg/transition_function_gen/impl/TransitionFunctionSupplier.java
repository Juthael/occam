package com.tregouet.occam.alg.transition_function_gen.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.preconcepts.IPreconceptTreeSupplier;
import com.tregouet.occam.alg.transition_function_gen.ITransitionFunctionSupplier;
import com.tregouet.occam.data.automata.IAutomaton;
import com.tregouet.occam.data.automata.machines.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.languages.specific.IStronglyContextualized;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.preconcepts.IPreconcepts;
import com.tregouet.tree_finder.data.Tree;

public abstract class TransitionFunctionSupplier implements ITransitionFunctionSupplier {

	protected static final int MAX_CAPACITY = 50;
	
	protected final IPreconcepts preconcepts;
	protected final IPreconceptTreeSupplier preconceptTreeSupplier;
	protected final DirectedAcyclicGraph<IDenotation, IStronglyContextualized> denotations;
	protected final Comparator<IAutomaton> functionComparator;
	
	public TransitionFunctionSupplier(IPreconcepts preconcepts, 
			DirectedAcyclicGraph<IDenotation, IStronglyContextualized> constructs) throws IOException {
		this.preconcepts = preconcepts;
		preconceptTreeSupplier = preconcepts.getConceptTreeSupplier();
		this.denotations = constructs;
		functionComparator = ScoreThenCostTFComparator.INSTANCE;
	}

	public static DirectedAcyclicGraph<IDenotation, IStronglyContextualized> getDenotationGraphFilteredByTreeOfDenotationSets(
			Tree<IPreconcept, IIsA> treeOfDenotationSets, 
			DirectedAcyclicGraph<IDenotation, IStronglyContextualized> unfilteredUnreduced) {
		DirectedAcyclicGraph<IDenotation, IStronglyContextualized> filtered =	
				new DirectedAcyclicGraph<>(null, null, false);
		List<IStronglyContextualized> edges = new ArrayList<>();
		List<IStronglyContextualized> varSwitchers = new ArrayList<>();
		List<IDenotation> varSwitcherSources = new ArrayList<>();
		for (IStronglyContextualized stronglyContextualized : unfilteredUnreduced.edgeSet()) {
			IPreconcept sourceDenotationSet = stronglyContextualized.getSourceConcept();
			IPreconcept targetDenotationSet = stronglyContextualized.getTargetConcept();
			if (treeOfDenotationSets.containsVertex(sourceDenotationSet) 
					&& treeOfDenotationSets.containsVertex(targetDenotationSet) 
					&& isA(sourceDenotationSet, targetDenotationSet, treeOfDenotationSets)) {
				if (stronglyContextualized.isVariableSwitcher()) {
					varSwitchers.add(stronglyContextualized);
					varSwitcherSources.add(stronglyContextualized.getSource());
				}
				else edges.add(stronglyContextualized);
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
		return filtered;
	}
	
	public static List<IStronglyContextualized> switchVariables(List<IStronglyContextualized> edges, List<IStronglyContextualized> varSwitchers){
		List<IStronglyContextualized> edgesReturned = new ArrayList<>(edges);
		List<IStronglyContextualized> edgesToRemove = new ArrayList<>();
		List<IStronglyContextualized> edgesToAdd = new ArrayList<>();
		IStronglyContextualized newProduction;
		for (IStronglyContextualized edge : edges) {
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
	
	private static boolean isA(IPreconcept denotationSet1, IPreconcept denotationSet2, Tree<IPreconcept, IIsA> treeOfDenotationSets) {
		return treeOfDenotationSets.getDescendants(denotationSet1).contains(denotationSet2);
	}

}
