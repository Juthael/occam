package com.tregouet.occam.alg.generation.representation.transitions_gen.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.TransitiveReduction;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.generation.representation.transitions_gen.IConceptTransitionsBuilder;
import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.alphabets.productions.impl.ContextualizedEpsilonProd;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.preconcepts.impl.ThisPreconcept;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.representations.properties.transitions.TransitionType;
import com.tregouet.occam.data.representations.properties.transitions.impl.Application;
import com.tregouet.occam.data.representations.properties.transitions.impl.ClosureTransition;
import com.tregouet.occam.data.representations.properties.transitions.impl.ConceptTransitionIC;
import com.tregouet.occam.data.representations.properties.transitions.impl.ConceptTransitionOIC;
import com.tregouet.occam.data.representations.properties.transitions.impl.InheritanceTransition;
import com.tregouet.occam.data.representations.properties.transitions.impl.InitialTransition;
import com.tregouet.occam.data.representations.properties.transitions.impl.SpontaneousTransition;
import com.tregouet.tree_finder.data.Tree;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import it.unimi.dsi.fastutil.ints.IntIntPair;

public class ConceptTransitionsBuilder implements IConceptTransitionsBuilder {

	public static final ConceptTransitionsBuilder INSTANCE = new ConceptTransitionsBuilder();
	
	private ConceptTransitionsBuilder() {
	}
	
	@Override
	public Set<IConceptTransition> buildApplicationsAndClosedInheritancesFrom(Tree<IPreconcept, IIsA> treeOfPreconcepts,
			Set<IContextualizedProduction> unfilteredUnreducedProds) {
		Set<IContextualizedProduction> mutableUnfilteredUnreduced = new HashSet<>(unfilteredUnreducedProds);
		Set<IContextualizedProduction> filteredProds = 
				filterProductionsWithTree(mutableUnfilteredUnreduced, treeOfPreconcepts);
		Set<IContextualizedProduction> filteredReducedProds = transitiveReduction(filteredProds);
		Set<IConceptTransition> transitions = new HashSet<>();
		Map<Integer, Integer> preconceptToSuccessorIDs = new HashMap<>();
		IPreconcept root = treeOfPreconcepts.getRoot();
		for (IPreconcept preconcept : treeOfPreconcepts.vertexSet()) {
			if (!preconcept.equals(root)) {
				preconceptToSuccessorIDs.put(
						preconcept.getID(), 
						Graphs.successorListOf(treeOfPreconcepts, preconcept).get(0).getID());
			}
		}
		for (IContextualizedProduction production : filteredReducedProds) {
			int outputStateID = production.getSpecies().getID();
			int inputStateID = preconceptToSuccessorIDs.get(outputStateID);
			if (production.isEpsilon())
				transitions.add(new InheritanceTransition(inputStateID, outputStateID, (ContextualizedEpsilonProd) production));
			else {
				AVariable poppedStackSymbol = production.getVariable();
				IConceptTransitionIC inputConfig = new ConceptTransitionIC(inputStateID, production, poppedStackSymbol);
				List<AVariable> pushedStackSymbols = production.getValue().getVariables();
				for (AVariable pushedStackSymbol : pushedStackSymbols) {
					IConceptTransitionOIC outputConfig = 
							new ConceptTransitionOIC(
									outputStateID, 
									new ArrayList<>(Arrays.asList(new AVariable[] {pushedStackSymbol})));
					transitions.add(new Application(inputConfig, outputConfig));
				}
			}			
		}
		return transitions;
	}
	
	@Override
	public Set<IConceptTransition> buildClosuresFrom(Set<IConceptTransition> transitions) {
		Set<IConceptTransition> closures = new HashSet<>();
		transitions.stream()
				.filter(t -> t.type() == TransitionType.APPLICATION)
				.forEach(a -> closures.add(
						new ClosureTransition(a.getInputConfiguration(), a.getOutputInternConfiguration().getOutputStateID())));
		return closures;
	}

	@Override
	public Set<IConceptTransition> buildUnclosedInheritancesFrom(Tree<IPreconcept, IIsA> treeOfPreconcepts) {
		Set<IConceptTransition> unclosedInheritances = new HashSet<>();
		IPreconcept root = treeOfPreconcepts.getRoot();
		for (IntIntPair genusToSpeciesID : getGenusToSpeciesIDs(root, treeOfPreconcepts)) {
			unclosedInheritances.add(new InheritanceTransition(genusToSpeciesID.firstInt(), genusToSpeciesID.secondInt()));
		}
		return unclosedInheritances;
	}
	
	@Override
	public Set<IConceptTransition> buildSpontaneousTransitionsFrom(Tree<IPreconcept, IIsA> treeOfPreconcepts) {
		Set<IConceptTransition> spontaneousTransitions = new HashSet<>();
		IPreconcept root = treeOfPreconcepts.getRoot();
		for (IntIntPair genusToSpeciesID : getGenusToSpeciesIDs(root, treeOfPreconcepts)) {
			spontaneousTransitions.add(new SpontaneousTransition(genusToSpeciesID.firstInt(), genusToSpeciesID.secondInt()));
		}
		return spontaneousTransitions;
	}
	
	private static Set<IntIntPair> getGenusToSpeciesIDs(IPreconcept genus, DirectedAcyclicGraph<IPreconcept, IIsA> graph) {
		Set<IntIntPair> genusToSpeciesIDs = new HashSet<>();
		int genusID = genus.getID();
		for (IPreconcept species : Graphs.predecessorListOf(graph, genus)) {
			genusToSpeciesIDs.add(new IntIntImmutablePair(genusID, species.getID()));
			genusToSpeciesIDs.addAll(getGenusToSpeciesIDs(species, graph));
		}
		return genusToSpeciesIDs;
	}
	
	private static Set<IContextualizedProduction> filterProductionsWithTree(
			Set<IContextualizedProduction> unfiltered, 
			Tree<IPreconcept, IIsA> treeOfPreconcepts) {
		Set<IContextualizedProduction> filtered = new HashSet<>();
		for (IContextualizedProduction production : unfiltered) {
			if (treeOfPreconcepts.containsVertex(production.getGenus()) 
					&& treeOfPreconcepts.containsVertex(production.getSpecies()))
				filtered.add(production);
		}
		return filtered;
	}
	
	private static Set<IContextualizedProduction> transitiveReduction(Set<IContextualizedProduction> unreduced){
		DirectedAcyclicGraph<IDenotation, IContextualizedProduction> prodGraph = new DirectedAcyclicGraph<>(null, null, false);
		for (IContextualizedProduction prod : unreduced) {
			IDenotation source = prod.getSource();
			IDenotation target = prod.getTarget();
			prodGraph.addVertex(source);
			prodGraph.addVertex(target);
			prodGraph.addEdge(source, target, prod);
		}
		TransitiveReduction.INSTANCE.reduce(prodGraph);
		return new HashSet<>(prodGraph.edgeSet());
	}

	@Override
	public IConceptTransition buildInitialTransition(Tree<IPreconcept, IIsA> treeOfPreconcepts) {
		ThisPreconcept thisPreconcept = (ThisPreconcept) treeOfPreconcepts.getRoot();
		return new InitialTransition(thisPreconcept);
	}

}
