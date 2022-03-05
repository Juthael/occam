package com.tregouet.occam.alg.transition_function_gen.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.concepts_gen.IConceptTreeSupplier;
import com.tregouet.occam.alg.transition_function_gen.ITransitionFunctionSupplier;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IDenotation;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.occam.data.languages.specific.IStronglyContextualized;
import com.tregouet.tree_finder.data.Tree;

public abstract class TransitionFunctionSupplier implements ITransitionFunctionSupplier {

	protected static final int MAX_CAPACITY = 50;
	
	protected final IConcepts concepts;
	protected final IConceptTreeSupplier conceptTreeSupplier;
	protected final DirectedAcyclicGraph<IDenotation, IStronglyContextualized> denotations;
	protected final Comparator<IAutomaton> functionComparator;
	
	public TransitionFunctionSupplier(IConcepts concepts, 
			DirectedAcyclicGraph<IDenotation, IStronglyContextualized> constructs) throws IOException {
		this.concepts = concepts;
		conceptTreeSupplier = concepts.getConceptTreeSupplier();
		this.denotations = constructs;
		functionComparator = ScoreThenCostTFComparator.INSTANCE;
	}

	public static DirectedAcyclicGraph<IDenotation, IStronglyContextualized> getDenotationGraphFilteredByTreeOfDenotationSets(
			Tree<IConcept, IIsA> treeOfDenotationSets, 
			DirectedAcyclicGraph<IDenotation, IStronglyContextualized> unfilteredUnreduced) {
		DirectedAcyclicGraph<IDenotation, IStronglyContextualized> filtered =	
				new DirectedAcyclicGraph<>(null, null, false);
		List<IStronglyContextualized> edges = new ArrayList<>();
		List<IStronglyContextualized> varSwitchers = new ArrayList<>();
		List<IDenotation> varSwitcherSources = new ArrayList<>();
		for (IStronglyContextualized stronglyContextualized : unfilteredUnreduced.edgeSet()) {
			IConcept sourceDenotationSet = stronglyContextualized.getSourceConcept();
			IConcept targetDenotationSet = stronglyContextualized.getTargetConcept();
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
	
	private static boolean isA(IConcept denotationSet1, IConcept denotationSet2, Tree<IConcept, IIsA> treeOfDenotationSets) {
		return treeOfDenotationSets.getDescendants(denotationSet1).contains(denotationSet2);
	}

}
