package com.tregouet.occam.alg.builders.pb_space.representations.classification_productions.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.classification_productions.ClassificationProductionSetBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.classification_productions.utils.ProductionSetReducer;
import com.tregouet.occam.alg.builders.pb_space.representations.lattice_productions.from_denotations.ProdBuilderFromDenotations;
import com.tregouet.occam.alg.builders.pb_space.representations.transition_functions.RepresentationTransFuncBuilder;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IComplementaryConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.impl.Denotation;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.states.productions.Salience;
import com.tregouet.occam.data.problem_space.states.productions.impl.ContextualizedProd;
import com.tregouet.occam.data.problem_space.states.productions.impl.ClassificationProductions;
import com.tregouet.tree_finder.data.InvertedTree;

public class FilterLatticeProductions implements ClassificationProductionSetBuilder {
	
	private Set<IContextualizedProduction> unfilteredUnreducedProds;
	
	public FilterLatticeProductions() {
	}

	@Override
	public ClassificationProductions apply(IClassification classification) {
		//every production generated from the concept lattice. New set to prevent side effects. 
		Set<IContextualizedProduction> mutableUnfilteredUnreduced = new HashSet<>(unfilteredUnreducedProds);
		/* if the genus or species concept of a production has been wrapped in a complementary concept while building 
		 * the tree, then change IDs accordingly */
		Set<IContextualizedProduction> updatedUnfilteredUnreduced = 
				updateProductionsWithComplementaryConceptsIDs(mutableUnfilteredUnreduced, classification.asGraph());	
		//restrict the previous set to the subset of productions relevant for this tree
		Set<IContextualizedProduction> filteredProds = filterProductionsWithTree(
				updatedUnfilteredUnreduced, classification);	
		/* add productions generated out of the tree's specific concepts (i.e., complementary concepts 
		 * that were not in the concept lattice and aren't wrapping any lattice concept. */
		Set<IContextualizedProduction> filteredUpdatedProds = addProductionsWithUnwrappingComplementaryConcepts(
				filteredProds, classification);	
		Set<IContextualizedProduction> filteredUpdatedReducedProds = ProductionSetReducer.reduce(filteredUpdatedProds);
		Map<IContextualizedProduction, Salience> prod2Salience = 
				ClassificationProductionSetBuilder.productionSalienceMapper().apply(classification, filteredUpdatedReducedProds);
		return new ClassificationProductions(prod2Salience);
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
			Integer newGenusID = wrappedConceptIDToComplementaryConceptID.get(prod.getSuperordinateID());
			Integer newSpeciesID = wrappedConceptIDToComplementaryConceptID.get(prod.getSubordinateID());
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
	
	private static Set<IContextualizedProduction> filterProductionsWithTree(Set<IContextualizedProduction> unfiltered,
			IClassification classification) {
		Set<IContextualizedProduction> filtered = new HashSet<>();
		InvertedTree<IConcept, IIsA> conceptTree = classification.asGraph();
		for (IContextualizedProduction production : unfiltered) {
			IConcept genus = classification.getConceptWithSpecifiedID(production.getSuperordinateID());;
			if (genus != null) {
				IConcept species = classification.getConceptWithSpecifiedID(production.getSubordinateID());
				if (species != null) {
					if (conceptTree.isStrictLowerBoundOf(species, genus))
						filtered.add(production);
				}
			}
		}
		return filtered;
	}
	
	private static Set<IContextualizedProduction> addProductionsWithUnwrappingComplementaryConcepts(
			Set<IContextualizedProduction> productions, IClassification classification) {
		Set<IIsA> adjacentEdgeToUnwrappingComplementary = new HashSet<>();
		/*find out transitions to or from unwrapping complementary concepts, since they haven't been taken 
		 *into account during the generation of productions from the concept lattice)
		 */
		InvertedTree<IConcept, IIsA> treeOfConcepts = classification.asGraph();
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
			ProdBuilderFromDenotations prodBuilder = 
					RepresentationTransFuncBuilder.getProdBuilderFromDenotations().setUp(species);
			for (IDenotation speciesDenotation : species.getDenotations()) {
				for (IDenotation genusDenotation : genus.getDenotations()) {
					productions.addAll(prodBuilder.apply(speciesDenotation, genusDenotation));
				}
			}
		}
		return productions;
	}

	@Override
	public ClassificationProductionSetBuilder setUp(Set<IContextualizedProduction> latticeProductions) {
		this.unfilteredUnreducedProds = latticeProductions;
		return this;
	}	

}
