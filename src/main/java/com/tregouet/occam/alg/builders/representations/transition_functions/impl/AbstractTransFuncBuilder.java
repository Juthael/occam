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

import com.tregouet.occam.alg.builders.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.representations.concepts.ConceptType;
import com.tregouet.occam.data.representations.concepts.IComplementaryConcept;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.concepts.denotations.IDenotation;
import com.tregouet.occam.data.representations.concepts.denotations.impl.Denotation;
import com.tregouet.occam.data.representations.concepts.impl.Everything;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionIC;
import com.tregouet.occam.data.representations.transitions.IConceptTransitionOIC;
import com.tregouet.occam.data.representations.transitions.IRepresentationTransitionFunction;
import com.tregouet.occam.data.representations.transitions.TransitionType;
import com.tregouet.occam.data.representations.transitions.dimensions.EpsilonDimension;
import com.tregouet.occam.data.representations.transitions.impl.Application;
import com.tregouet.occam.data.representations.transitions.impl.ClosureTransition;
import com.tregouet.occam.data.representations.transitions.impl.ConceptTransitionIC;
import com.tregouet.occam.data.representations.transitions.impl.ConceptTransitionOIC;
import com.tregouet.occam.data.representations.transitions.impl.InheritanceTransition;
import com.tregouet.occam.data.representations.transitions.impl.InitialTransition;
import com.tregouet.occam.data.representations.transitions.impl.RepresentationTransitionFunction;
import com.tregouet.occam.data.representations.transitions.impl.SpontaneousTransition;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;
import com.tregouet.occam.data.representations.transitions.productions.impl.ContextualizedProd;
import com.tregouet.tree_finder.data.InvertedTree;

import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;
import it.unimi.dsi.fastutil.ints.IntIntPair;

public abstract class AbstractTransFuncBuilder implements RepresentationTransFuncBuilder {

	private InvertedTree<IConcept, IIsA> treeOfConcepts = null;
	private Set<IContextualizedProduction> unfilteredUnreducedProds = null;

	public AbstractTransFuncBuilder() {
	}

	private static Set<IConceptTransition> buildApplicationsAndUnclosedInheritancesFrom(
			InvertedTree<IConcept, IIsA> treeOfConcepts, Set<IContextualizedProduction> unfilteredUnreducedProds) {
		//every production generated from the concept lattice. New set to prevent side effects. 
		Set<IContextualizedProduction> mutableUnfilteredUnreduced = new HashSet<>(unfilteredUnreducedProds);
		/* if the genus or species concept of a production has been wrapped in a complementary concept while building 
		 * the tree, then change IDs accordingly */
		Set<IContextualizedProduction> updatedUnfilteredUnreduced = 
				updateProductionsWithComplementaryConceptsIDs(mutableUnfilteredUnreduced, treeOfConcepts);
		//restrict the previous set to the subset of productions relevant for this tree
		Set<IContextualizedProduction> filteredProds = filterProductionsWithTree(
				updatedUnfilteredUnreduced, treeOfConcepts);
		/* add productions generated out of the tree's specific concepts (i.e., complementary concepts 
		 * that were not in the concept lattice and aren't wrapping any lattice concept. */
		Set<IContextualizedProduction> filteredUpdatedProds = addProductionsWithUnwrappingComplementaryConcepts(
				filteredProds, treeOfConcepts);
		Set<IContextualizedProduction> filteredUpdatedReducedProds = transitiveReduction(filteredUpdatedProds);
		Set<IConceptTransition> transitions = new HashSet<>();
		//map each concept to its unique successor
		Map<Integer, Integer> conceptToSuccessorIDs = new HashMap<>();
		IConcept root = treeOfConcepts.getRoot();
		for (IConcept concept : treeOfConcepts.vertexSet()) {
			if (!concept.equals(root)) {
				conceptToSuccessorIDs.put(concept.iD(), Graphs.successorListOf(treeOfConcepts, concept).get(0).iD());
			}
		}
		for (IContextualizedProduction production : filteredUpdatedReducedProds) {
			if (!production.isEpsilon()) {
				int outputStateID = production.getSpeciesID();
				int inputStateID = conceptToSuccessorIDs.get(outputStateID);
				if (production.isBlank()) {
					//blank : substitution of a variable by itself. Instead, ε-transition with same variable remaining on top
					transitions.add(new InheritanceTransition(inputStateID, outputStateID, production.getSource(), 
							production.getTarget(), production.getVariable()));
				}
				else {
					AVariable poppedStackSymbol = production.getVariable();
					IConceptTransitionIC inputConfig = new ConceptTransitionIC(inputStateID, production, poppedStackSymbol);
					List<AVariable> newBoundVariables = production.getValue().getVariables();
					if (newBoundVariables.isEmpty()) {
						IConceptTransitionOIC outputConfig = new ConceptTransitionOIC(outputStateID,
								new ArrayList<>(Arrays.asList(new AVariable[] {EpsilonDimension.INSTANCE})));
						transitions.add(new Application(inputConfig, outputConfig));
					}
					else for (AVariable pushedStackSymbol : newBoundVariables) {
						IConceptTransitionOIC outputConfig = new ConceptTransitionOIC(outputStateID,
								new ArrayList<>(Arrays.asList(new AVariable[] {pushedStackSymbol})));
						transitions.add(new Application(inputConfig, outputConfig));
					}
				}
			}
		}
		return transitions;
	}

	private static Set<IConceptTransition> buildClosures(Set<IConceptTransition> applications) {
		Set<IConceptTransition> closures = new HashSet<>();
		for (IConceptTransition application : applications) {
			IConceptTransitionIC inputConfig = application.getInputConfiguration();
			if (inputConfig.getInputSymbol().getValue().getVariables().size() > 0)
				closures.add(new ClosureTransition(inputConfig,
					application.getOutputInternConfiguration().getOutputStateID()));
		}
		return closures;
	}

	private static IConceptTransition buildInitialTransition(InvertedTree<IConcept, IIsA> treeOfConcepts) {
		Everything everything = (Everything) treeOfConcepts.getRoot();
		return new InitialTransition(everything);
	}

	private static Set<IConceptTransition> buildSpontaneousTransitions(
			InvertedTree<IConcept, IIsA> treeOfConcepts) {
		Set<IConceptTransition> spontaneousTransitions = new HashSet<>();
		IConcept root = treeOfConcepts.getRoot();
		for (IntIntPair genusToSpeciesID : getGenusToSpeciesIDs(root, treeOfConcepts)) {
			spontaneousTransitions
					.add(new SpontaneousTransition(genusToSpeciesID.firstInt(), genusToSpeciesID.secondInt()));
		}
		return spontaneousTransitions;
	}

	private static Set<IConceptTransition> buildClosedInheritances(InvertedTree<IConcept, IIsA> treeOfConcepts) {
		Set<IConceptTransition> unclosedInheritances = new HashSet<>();
		IConcept truism = null;
		List<IConcept> topoOrder = treeOfConcepts.getTopologicalOrder();
		int idx = topoOrder.size() - 1;
		while (truism == null) {
			IConcept next = topoOrder.get(idx--);
			if (next.type() == ConceptType.TRUISM)
				truism = next;
		}
		for (IntIntPair genusToSpeciesID : getGenusToSpeciesIDs(truism, treeOfConcepts)) {
			unclosedInheritances
					.add(new InheritanceTransition(genusToSpeciesID.firstInt(), genusToSpeciesID.secondInt()));
		}
		return unclosedInheritances;
	}

	private static Set<IContextualizedProduction> filterProductionsWithTree(Set<IContextualizedProduction> unfiltered,
			InvertedTree<IConcept, IIsA> treeOfConcepts) {
		Set<IContextualizedProduction> filtered = new HashSet<>();
		for (IContextualizedProduction production : unfiltered) {
			IConcept genus = getVertexWithID(treeOfConcepts, production.getGenusID());
			if (genus != null) {
				IConcept species = getVertexWithID(treeOfConcepts, production.getSpeciesID());
				if (species != null) {
					if (treeOfConcepts.isStrictLowerBoundOf(species, genus))
						filtered.add(production);
				}
			}
		}
		return filtered;
	}

	private static Set<IntIntPair> getGenusToSpeciesIDs(IConcept genus, DirectedAcyclicGraph<IConcept, IIsA> graph) {
		Set<IntIntPair> genusToSpeciesIDs = new HashSet<>();
		int genusID = genus.iD();
		for (IConcept species : Graphs.predecessorListOf(graph, genus)) {
			genusToSpeciesIDs.add(new IntIntImmutablePair(genusID, species.iD()));
			genusToSpeciesIDs.addAll(getGenusToSpeciesIDs(species, graph));
		}
		return genusToSpeciesIDs;
	}

	private static Set<IContextualizedProduction> transitiveReduction(Set<IContextualizedProduction> unreduced) {
		DirectedAcyclicGraph<IDenotation, IContextualizedProduction> prodGraph = new DirectedAcyclicGraph<>(null, null,
				false);
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
	public IRepresentationTransitionFunction apply(InvertedTree<IConcept, IIsA> treeOfConcepts,
			Set<IContextualizedProduction> unfilteredUnreducedProds) {
		this.treeOfConcepts = treeOfConcepts;
		this.unfilteredUnreducedProds = unfilteredUnreducedProds;
		return output();
	}

	abstract protected Set<IConceptTransition> filter(Set<IConceptTransition> transitions);

	private IRepresentationTransitionFunction output() {
		// declare TF constructor parameters
		IConceptTransition initial;
		Set<IConceptTransition> applications = new HashSet<>();
		Set<IConceptTransition> closures;
		Set<IConceptTransition> inheritances = new HashSet<>();
		Set<IConceptTransition> spontaneous;
		// build
		initial = buildInitialTransition(treeOfConcepts);
		Set<IConceptTransition> appAndClosedInheritances = buildApplicationsAndUnclosedInheritancesFrom(treeOfConcepts,
				unfilteredUnreducedProds);
		for (IConceptTransition transition : appAndClosedInheritances) {
			if (transition.type() == TransitionType.APPLICATION)
				applications.add(transition);
			else
				inheritances.add(transition);
		}
		closures = buildClosures(applications);
		inheritances.addAll(buildClosedInheritances(treeOfConcepts));
		spontaneous = buildSpontaneousTransitions(treeOfConcepts);
		// set saliences
		Set<IConceptTransition> transitions = new HashSet<>();
		transitions.add(initial);
		transitions.addAll(applications);
		transitions.addAll(closures);
		transitions.addAll(inheritances);
		transitions.addAll(spontaneous);
		RepresentationTransFuncBuilder.transitionSalienceSetter().accept(transitions);
		// return
		return new RepresentationTransitionFunction(transitions);
	}
	
	private static IConcept getVertexWithID(InvertedTree<IConcept, IIsA> treeOfConcepts, int iD) {
		for (IConcept concept : treeOfConcepts) {
			if (concept.iD() == iD)
				return concept;
		}
		return null;
	}
	
	private static Set<IContextualizedProduction> addProductionsWithUnwrappingComplementaryConcepts(
			Set<IContextualizedProduction> productions, 
			InvertedTree<IConcept, IIsA> treeOfConcepts) {
		Set<IIsA> adjacentEdgeToUnwrappingComplementary = new HashSet<>();
		/*find out transitions to or from unwrapping complementary concepts, since they haven't been taken 
		 *into account during the generation of productions from the concept lattice)
		 */
		for (IIsA edge : treeOfConcepts.edgeSet()) {
			IConcept species = treeOfConcepts.getEdgeSource(edge);
			if (species.isComplementary() && ((IComplementaryConcept) species).getWrappedComplementing() == null)
				adjacentEdgeToUnwrappingComplementary.add(edge);
			else {
				IConcept genus = treeOfConcepts.getEdgeTarget(edge);
				if (genus.isComplementary() && ((IComplementaryConcept) genus).getWrappedComplementing() == null)
					adjacentEdgeToUnwrappingComplementary.add(edge);
			}
		}
		//build productions
		for (IIsA edge : adjacentEdgeToUnwrappingComplementary) {
			IConcept species = treeOfConcepts.getEdgeSource(edge);
			IConcept genus = treeOfConcepts.getEdgeTarget(edge);
			for (IDenotation speciesDenotation : species.getDenotations()) {
				for (IDenotation genusDenotation : genus.getDenotations()) {
					productions.addAll(
							RepresentationTransFuncBuilder.getProdBuilderFromDenotations().apply(
									speciesDenotation, genusDenotation));
				}
			}
		}
		return productions;
	}
	
	private static Set<IContextualizedProduction> updateProductionsWithComplementaryConceptsIDs(
			Set<IContextualizedProduction> productions, 
			InvertedTree<IConcept, IIsA> treeOfConcepts) {
		Set<IContextualizedProduction> updatedProductions = new HashSet<>();
		//map wrapped concepts to complementary wrapping concepts
		Map<Integer, Integer> wrappedConceptIDToComplementaryConceptID = new HashMap<>();
		for (IConcept concept : treeOfConcepts) {
			if (concept.isComplementary()) {
				IConcept wrapped = ((IComplementaryConcept) concept).getWrappedComplementing();
				if (wrapped != null)
					wrappedConceptIDToComplementaryConceptID.put(wrapped.iD(), concept.iD());
			}
		}
		//update production IDs
		for (IContextualizedProduction prod : productions) {
			Integer newGenusID = wrappedConceptIDToComplementaryConceptID.get(prod.getGenusID());
			Integer newSpeciesID = wrappedConceptIDToComplementaryConceptID.get(prod.getSpeciesID());
			updatedProductions.add(updateOrReturnUnchanged(prod, newGenusID, newSpeciesID));
		}
		return updatedProductions;
	}	
	
	private static IContextualizedProduction updateOrReturnUnchanged(IContextualizedProduction production, 
			Integer newGenusID, Integer newSpeciesID) {
		if (newGenusID == null && newSpeciesID == null)
			return production;
		IDenotation speciesDenotation;
		IDenotation genusDenotation;
		if (newGenusID != null)
			genusDenotation = new Denotation(production.getTarget(), newGenusID);
		else genusDenotation = production.getTarget();
		if (newSpeciesID != null)
			speciesDenotation = new Denotation(production.getSource(), newSpeciesID);
		else speciesDenotation = production.getSource();
		return new ContextualizedProd(speciesDenotation, genusDenotation, production);
	}	

}
