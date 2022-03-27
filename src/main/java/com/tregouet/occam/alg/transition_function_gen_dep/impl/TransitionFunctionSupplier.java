package com.tregouet.occam.alg.transition_function_gen_dep.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.representations.concept_trees.ConceptTreeBuilder;
import com.tregouet.occam.alg.transition_function_gen_dep.ITransitionFunctionSupplier;
import com.tregouet.occam.data.languages.words.fact.IStronglyContextualized;
import com.tregouet.occam.data.logical_structures.automata.IAutomaton;
import com.tregouet.occam.data.logical_structures.automata.machines.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IDenotation;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedTree;

public abstract class TransitionFunctionSupplier implements ITransitionFunctionSupplier {

	protected static final int MAX_CAPACITY = 50;
	
	protected final IConceptLattice conceptLattice;
	protected final ConceptTreeBuilder conceptTreeBuilder;
	protected final DirectedAcyclicGraph<IDenotation, IStronglyContextualized> denotations;
	protected final Comparator<IAutomaton> functionComparator;
	
	public TransitionFunctionSupplier(IConceptLattice conceptLattice, 
			DirectedAcyclicGraph<IDenotation, IStronglyContextualized> constructs) throws IOException {
		this.conceptLattice = conceptLattice;
		conceptTreeBuilder = conceptLattice.getConceptTreeSupplier();
		this.denotations = constructs;
		functionComparator = ScoreThenCostTFComparator.INSTANCE;
	}

	public static DirectedAcyclicGraph<IDenotation, IStronglyContextualized> getDenotationGraphFilteredByTreeOfDenotationSets(
			InvertedTree<IConcept, IIsA> treeOfDenotationSets, 
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
	
	private static boolean isA(IConcept denotationSet1, IConcept denotationSet2, InvertedTree<IConcept, IIsA> treeOfDenotationSets) {
		return treeOfDenotationSets.getDescendants(denotationSet1).contains(denotationSet2);
	}

}
