package com.tregouet.occam.alg.builders.pb_space.representations.production_sets.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.ProductionSetBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.productions.ProductionBuilder;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.ConceptType;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.tree_finder.data.InvertedTree;

public class BuildFromScratchNoEpsilon implements ProductionSetBuilder {
	
	private Map<Integer, Set<IDenotation>> conceptID2ReducedDenotSet = null;
	
	public BuildFromScratchNoEpsilon() {
	}
	
	@Override
	public Set<IContextualizedProduction> apply(IClassification classification) {
		setUp(classification);
		Set<IContextualizedProduction> prods = buildProductions(classification);
		ProductionSetBuilder.productionSalienceSetter().setUp(classification).accept(prods);
		return prods;
	}
	
	private Set<IContextualizedProduction> buildProductions(IClassification classification) {
		Set<IContextualizedProduction> productions = new HashSet<>();
		IConcept root = classification.asGraph().getRoot();
		IDenotation ontologicalCommitment = new ArrayList<>(root.getDenotations()).get(0);
		for (IConcept species : classification.asGraph().vertexSet()) {
			if (species.type() != ConceptType.ONTOLOGICAL_COMMITMENT) {
				Set<IDenotation> uncomputed = conceptID2ReducedDenotSet.get(species.iD());
				ProductionBuilder builder = ProductionSetBuilder.productionBuilder().setUp(species);
				IConcept genus = classification.getGenus(species);
				for (IDenotation source : species.getDenotations()) {
					if (!source.isArbitraryLabel()) {
						boolean sourceIsAbstract = source.isAbstract();
						boolean computationIsCompulsory = uncomputed.contains(source);
						boolean computationProceeded = false;
						for (IDenotation target : genus.getDenotations()) {
							if (sourceIsAbstract && source.asList().equals(target.asList())) {
								productions.addAll(builder.apply(source, target));
								computationProceeded = true;
							}
							else if (computationIsCompulsory) {
								Set<IContextualizedProduction> computations = builder.apply(source, target);
								if (!computations.isEmpty()) {
									productions.addAll(computations);
									computationProceeded = true;
								}
							}
						}
						if (computationIsCompulsory && !computationProceeded) {
							productions.addAll(builder.apply(source, ontologicalCommitment));
						}
					}
				}
			}
		}
		return productions;
	}
	
	private void setUp(IClassification classification) {
		conceptID2ReducedDenotSet = new HashMap<>();
		InvertedTree<IConcept, IIsA> classGraph = classification.asGraph();
		for (IConcept concept : classGraph.vertexSet()) {
			conceptID2ReducedDenotSet.put(concept.iD(), new HashSet<>(concept.getDenotations()));
		}
		Set<Integer> classLeafIDs = new HashSet<>();
		for (IConcept leaf : classGraph.getLeaves())
			classLeafIDs.add(leaf.iD());
		for (IConcept superordinate : Lists.reverse(classGraph.getTopologicalOrder())) {
			if (!classLeafIDs.contains(superordinate.iD())) {
				Set<IDenotation> supDenot = conceptID2ReducedDenotSet.get(superordinate.iD());
				for (IConcept subordinate : classGraph.getAncestors(superordinate)) {
					Set<IDenotation> subDenot = conceptID2ReducedDenotSet.get(subordinate.iD());
					removeInherited(supDenot, subDenot);
				}	
			}
		}
	}
	
	private void removeInherited(Set<IDenotation> supDenot, Set<IDenotation> subDenot) {
		Set<IDenotation> toBeRemoved = new HashSet<>();
		for (IDenotation sup : supDenot) {
			List<ISymbol> supList = sup.asList();
			for (IDenotation sub : subDenot) {
				if (supList.equals(sub.asList())) {
					toBeRemoved.add(sub);
					break;
				}
			}
		}
		subDenot.removeAll(toBeRemoved);
	}	

}
