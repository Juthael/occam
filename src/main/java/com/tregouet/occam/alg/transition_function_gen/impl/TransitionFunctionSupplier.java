package com.tregouet.occam.alg.transition_function_gen.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.denotation_sets_gen.IConceptTreeSupplier;
import com.tregouet.occam.alg.transition_function_gen.ITransitionFunctionSupplier;
import com.tregouet.occam.data.denotations.IConcept;
import com.tregouet.occam.data.denotations.IConcepts;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.utils.ScoreThenCostTFComparator;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.occam.data.languages.specific.IBasicProductionAsEdge;
import com.tregouet.tree_finder.data.Tree;

public abstract class TransitionFunctionSupplier implements ITransitionFunctionSupplier {

	protected static final int MAX_CAPACITY = 50;
	
	protected final IConcepts concepts;
	protected final IConceptTreeSupplier conceptTreeSupplier;
	protected final DirectedAcyclicGraph<IDenotation, IBasicProductionAsEdge> denotations;
	protected final Comparator<IAutomaton> functionComparator;
	
	public TransitionFunctionSupplier(IConcepts concepts, 
			DirectedAcyclicGraph<IDenotation, IBasicProductionAsEdge> constructs) throws IOException {
		this.concepts = concepts;
		conceptTreeSupplier = concepts.getConceptTreeSupplier();
		this.denotations = constructs;
		functionComparator = ScoreThenCostTFComparator.INSTANCE;
	}

	public static DirectedAcyclicGraph<IDenotation, IBasicProductionAsEdge> getDenotationGraphFilteredByTreeOfDenotationSets(
			Tree<IConcept, IIsA> treeOfDenotationSets, 
			DirectedAcyclicGraph<IDenotation, IBasicProductionAsEdge> unfilteredUnreduced) {
		DirectedAcyclicGraph<IDenotation, IBasicProductionAsEdge> filtered =	
				new DirectedAcyclicGraph<>(null, null, false);
		List<IBasicProductionAsEdge> edges = new ArrayList<>();
		List<IBasicProductionAsEdge> varSwitchers = new ArrayList<>();
		List<IDenotation> varSwitcherSources = new ArrayList<>();
		for (IBasicProductionAsEdge basicProductionAsEdge : unfilteredUnreduced.edgeSet()) {
			IConcept sourceDenotationSet = basicProductionAsEdge.getSourceDenotationSet();
			IConcept targetDenotationSet = basicProductionAsEdge.getTargetDenotationSet();
			if (treeOfDenotationSets.containsVertex(sourceDenotationSet) 
					&& treeOfDenotationSets.containsVertex(targetDenotationSet) 
					&& isA(sourceDenotationSet, targetDenotationSet, treeOfDenotationSets)) {
				if (basicProductionAsEdge.isVariableSwitcher()) {
					varSwitchers.add(basicProductionAsEdge);
					varSwitcherSources.add(basicProductionAsEdge.getSource());
				}
				else edges.add(basicProductionAsEdge);
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
	
	public static List<IBasicProductionAsEdge> switchVariables(List<IBasicProductionAsEdge> edges, List<IBasicProductionAsEdge> varSwitchers){
		List<IBasicProductionAsEdge> edgesReturned = new ArrayList<>(edges);
		List<IBasicProductionAsEdge> edgesToRemove = new ArrayList<>();
		List<IBasicProductionAsEdge> edgesToAdd = new ArrayList<>();
		IBasicProductionAsEdge newProduction;
		for (IBasicProductionAsEdge edge : edges) {
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
