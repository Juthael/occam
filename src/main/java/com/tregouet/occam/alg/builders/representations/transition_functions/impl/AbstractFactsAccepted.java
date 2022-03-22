package com.tregouet.occam.alg.builders.representations.transition_functions.impl;

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

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.representations.transition_functions.IRepresentationTransFuncBuilder;
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
import com.tregouet.occam.data.representations.properties.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.representations.properties.transitions.TransitionType;
import com.tregouet.occam.data.representations.properties.transitions.impl.Application;
import com.tregouet.occam.data.representations.properties.transitions.impl.ClosureTransition;
import com.tregouet.occam.data.representations.properties.transitions.impl.ConceptTransitionIC;
import com.tregouet.occam.data.representations.properties.transitions.impl.ConceptTransitionOIC;
import com.tregouet.occam.data.representations.properties.transitions.impl.InheritanceTransition;
import com.tregouet.occam.data.representations.properties.transitions.impl.InitialTransition;
import com.tregouet.occam.data.representations.properties.transitions.impl.RepresentationTransitionFunction;
import com.tregouet.occam.data.representations.properties.transitions.impl.SpontaneousTransition;
import com.tregouet.tree_finder.data.Tree;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import it.unimi.dsi.fastutil.ints.IntIntPair;

public class AbstractFactsAccepted implements IRepresentationTransFuncBuilder {

	
	private Tree<IPreconcept, IIsA> treeOfPreconcepts = null;
	private Set<IContextualizedProduction> unfilteredUnreducedProds = null;
	
	public AbstractFactsAccepted() {
	}
	
	@Override
	public IRepresentationTransFuncBuilder input(Tree<IPreconcept, IIsA> treeOfPreconcepts,
			Set<IContextualizedProduction> unfilteredUnreducedProds) {
		this.treeOfPreconcepts = treeOfPreconcepts;
		this.unfilteredUnreducedProds = unfilteredUnreducedProds;
		return this;
	}

	@Override
	public IRepresentationTransitionFunction output() {
		//declare TF constructor parameters
		IConceptTransition initial;
		Set<IConceptTransition> applications = new HashSet<>();
		Set<IConceptTransition> closures;
		Set<IConceptTransition> inheritances = new HashSet<>();
		Set<IConceptTransition> spontaneous;
		//build
		initial = buildInitialTransition(treeOfPreconcepts);
		Set<IConceptTransition> appAndClosedInheritances = 
				buildApplicationsAndClosedInheritancesFrom(treeOfPreconcepts, unfilteredUnreducedProds);
		for (IConceptTransition transition : appAndClosedInheritances) {
			if (transition.type() == TransitionType.APPLICATION)
				applications.add(transition);
			else inheritances.add(transition);
		}
		closures = buildClosuresFrom(applications);
		inheritances.addAll(buildUnclosedInheritancesFrom(treeOfPreconcepts));
		spontaneous = buildSpontaneousTransitionsFrom(treeOfPreconcepts);
		//set saliences
		Set<IConceptTransition> transitions = new HashSet<>();
		transitions.add(initial);
		transitions.addAll(applications);
		transitions.addAll(closures);
		transitions.addAll(inheritances);
		transitions.addAll(spontaneous);
		GeneratorsAbstractFactory.INSTANCE.getTransitionSalienceSetter().setTransitionSaliencesOf(transitions);
		//return 
		return new RepresentationTransitionFunction(transitions);
	}
	
	private static Set<IConceptTransition> buildApplicationsAndClosedInheritancesFrom(Tree<IPreconcept, IIsA> treeOfPreconcepts,
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
	
	private static Set<IConceptTransition> buildClosuresFrom(Set<IConceptTransition> applications) {
		Set<IConceptTransition> closures = new HashSet<>();
		for (IConceptTransition application : applications)
			closures.add(
					new ClosureTransition(
							application.getInputConfiguration(), 
							application.getOutputInternConfiguration().getOutputStateID()));
		return closures;
	}

	private static Set<IConceptTransition> buildUnclosedInheritancesFrom(Tree<IPreconcept, IIsA> treeOfPreconcepts) {
		Set<IConceptTransition> unclosedInheritances = new HashSet<>();
		IPreconcept root = treeOfPreconcepts.getRoot();
		for (IntIntPair genusToSpeciesID : getGenusToSpeciesIDs(root, treeOfPreconcepts)) {
			unclosedInheritances.add(new InheritanceTransition(genusToSpeciesID.firstInt(), genusToSpeciesID.secondInt()));
		}
		return unclosedInheritances;
	}
	
	private static Set<IConceptTransition> buildSpontaneousTransitionsFrom(Tree<IPreconcept, IIsA> treeOfPreconcepts) {
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

	private static IConceptTransition buildInitialTransition(Tree<IPreconcept, IIsA> treeOfPreconcepts) {
		ThisPreconcept thisPreconcept = (ThisPreconcept) treeOfPreconcepts.getRoot();
		return new InitialTransition(thisPreconcept);
	}	

}
