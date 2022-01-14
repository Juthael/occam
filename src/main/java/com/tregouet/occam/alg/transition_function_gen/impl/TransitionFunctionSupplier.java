package com.tregouet.occam.alg.transition_function_gen.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.denotation_sets_gen.IDenotationSetsTreeSupplier;
import com.tregouet.occam.alg.transition_function_gen.ITransitionFunctionSupplier;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotationSets;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.languages.specific.IProduction;
import com.tregouet.tree_finder.data.Tree;

public abstract class TransitionFunctionSupplier implements ITransitionFunctionSupplier {

	protected static final int MAX_CAPACITY = 50;
	
	protected final IDenotationSets denotationSets;
	protected final IDenotationSetsTreeSupplier denotationSetsTreeSupplier;
	protected final DirectedAcyclicGraph<IDenotation, IProduction> denotations;
	protected final Comparator<IAutomaton> functionComparator;
	
	public TransitionFunctionSupplier(IDenotationSets denotationSets, 
			DirectedAcyclicGraph<IDenotation, IProduction> constructs) throws IOException {
		this.denotationSets = denotationSets;
		denotationSetsTreeSupplier = denotationSets.getDenotationSetsTreeSupplier();
		this.denotations = constructs;
		functionComparator = ScoreThenCostTFComparator.INSTANCE;
	}

	public static DirectedAcyclicGraph<IDenotation, IProduction> getDenotationGraphFilteredByTreeOfDenotationSets(
			Tree<IDenotationSet, IIsA> treeOfDenotationSets, 
			DirectedAcyclicGraph<IDenotation, IProduction> unfilteredUnreduced) {
		DirectedAcyclicGraph<IDenotation, IProduction> filtered =	
				new DirectedAcyclicGraph<>(null, null, false);
		List<IProduction> edges = new ArrayList<>();
		List<IProduction> varSwitchers = new ArrayList<>();
		List<IDenotation> varSwitcherSources = new ArrayList<>();
		for (IProduction production : unfilteredUnreduced.edgeSet()) {
			IDenotationSet sourceDenotationSet = production.getSourceDenotationSet();
			IDenotationSet targetDenotationSet = production.getTargetDenotationSet();
			if (treeOfDenotationSets.containsVertex(sourceDenotationSet) 
					&& treeOfDenotationSets.containsVertex(targetDenotationSet) 
					&& isA(sourceDenotationSet, targetDenotationSet, treeOfDenotationSets)) {
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
	
	private static boolean isA(IDenotationSet denotationSet1, IDenotationSet denotationSet2, Tree<IDenotationSet, IIsA> treeOfDenotationSets) {
		return treeOfDenotationSets.getDescendants(denotationSet1).contains(denotationSet2);
	}

}
