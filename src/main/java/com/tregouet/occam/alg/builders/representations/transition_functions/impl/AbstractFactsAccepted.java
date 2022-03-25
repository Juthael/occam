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
import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.languages.alphabets.domain_specific.impl.ContextualizedEpsilonProd;
import com.tregouet.occam.data.languages.alphabets.generic.AVariable;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IDenotation;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.concepts.impl.Everything;
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

	
	private Tree<IConcept, IIsA> treeOfConcepts = null;
	private Set<IContextualizedProduction> unfilteredUnreducedProds = null;
	
	public AbstractFactsAccepted() {
	}
	
	@Override
	public IRepresentationTransFuncBuilder input(Tree<IConcept, IIsA> treeOfConcepts,
			Set<IContextualizedProduction> unfilteredUnreducedProds) {
		this.treeOfConcepts = treeOfConcepts;
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
		initial = buildInitialTransition(treeOfConcepts);
		Set<IConceptTransition> appAndClosedInheritances = 
				buildApplicationsAndClosedInheritancesFrom(treeOfConcepts, unfilteredUnreducedProds);
		for (IConceptTransition transition : appAndClosedInheritances) {
			if (transition.type() == TransitionType.APPLICATION)
				applications.add(transition);
			else inheritances.add(transition);
		}
		closures = buildClosuresFrom(applications);
		inheritances.addAll(buildUnclosedInheritancesFrom(treeOfConcepts));
		spontaneous = buildSpontaneousTransitionsFrom(treeOfConcepts);
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
	
	private static Set<IConceptTransition> buildApplicationsAndClosedInheritancesFrom(Tree<IConcept, IIsA> treeOfConcepts,
			Set<IContextualizedProduction> unfilteredUnreducedProds) {
		Set<IContextualizedProduction> mutableUnfilteredUnreduced = new HashSet<>(unfilteredUnreducedProds);
		Set<IContextualizedProduction> filteredProds = 
				filterProductionsWithTree(mutableUnfilteredUnreduced, treeOfConcepts);
		Set<IContextualizedProduction> filteredReducedProds = transitiveReduction(filteredProds);
		Set<IConceptTransition> transitions = new HashSet<>();
		Map<Integer, Integer> conceptToSuccessorIDs = new HashMap<>();
		IConcept root = treeOfConcepts.getRoot();
		for (IConcept concept : treeOfConcepts.vertexSet()) {
			if (!concept.equals(root)) {
				conceptToSuccessorIDs.put(
						concept.getID(), 
						Graphs.successorListOf(treeOfConcepts, concept).get(0).getID());
			}
		}
		for (IContextualizedProduction production : filteredReducedProds) {
			int outputStateID = production.getSpecies().getID();
			int inputStateID = conceptToSuccessorIDs.get(outputStateID);
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

	private static Set<IConceptTransition> buildUnclosedInheritancesFrom(Tree<IConcept, IIsA> treeOfConcepts) {
		Set<IConceptTransition> unclosedInheritances = new HashSet<>();
		IConcept root = treeOfConcepts.getRoot();
		for (IntIntPair genusToSpeciesID : getGenusToSpeciesIDs(root, treeOfConcepts)) {
			unclosedInheritances.add(new InheritanceTransition(genusToSpeciesID.firstInt(), genusToSpeciesID.secondInt()));
		}
		return unclosedInheritances;
	}
	
	private static Set<IConceptTransition> buildSpontaneousTransitionsFrom(Tree<IConcept, IIsA> treeOfConcepts) {
		Set<IConceptTransition> spontaneousTransitions = new HashSet<>();
		IConcept root = treeOfConcepts.getRoot();
		for (IntIntPair genusToSpeciesID : getGenusToSpeciesIDs(root, treeOfConcepts)) {
			spontaneousTransitions.add(new SpontaneousTransition(genusToSpeciesID.firstInt(), genusToSpeciesID.secondInt()));
		}
		return spontaneousTransitions;
	}
	
	private static Set<IntIntPair> getGenusToSpeciesIDs(IConcept genus, DirectedAcyclicGraph<IConcept, IIsA> graph) {
		Set<IntIntPair> genusToSpeciesIDs = new HashSet<>();
		int genusID = genus.getID();
		for (IConcept species : Graphs.predecessorListOf(graph, genus)) {
			genusToSpeciesIDs.add(new IntIntImmutablePair(genusID, species.getID()));
			genusToSpeciesIDs.addAll(getGenusToSpeciesIDs(species, graph));
		}
		return genusToSpeciesIDs;
	}
	
	private static Set<IContextualizedProduction> filterProductionsWithTree(
			Set<IContextualizedProduction> unfiltered, 
			Tree<IConcept, IIsA> treeOfConcepts) {
		Set<IContextualizedProduction> filtered = new HashSet<>();
		for (IContextualizedProduction production : unfiltered) {
			if (treeOfConcepts.containsVertex(production.getGenus()) 
					&& treeOfConcepts.containsVertex(production.getSpecies()))
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

	private static IConceptTransition buildInitialTransition(Tree<IConcept, IIsA> treeOfConcepts) {
		Everything everything = (Everything) treeOfConcepts.getRoot();
		return new InitialTransition(everything);
	}	

}
