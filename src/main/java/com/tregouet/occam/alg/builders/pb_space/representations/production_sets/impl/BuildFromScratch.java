package com.tregouet.occam.alg.builders.pb_space.representations.production_sets.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.ProductionSetBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.productions.ProductionBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.utils.ProductionSetReducer;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.tree_finder.data.InvertedTree;

public class BuildFromScratch implements ProductionSetBuilder {

	public static final BuildFromScratch INSTANCE = new BuildFromScratch();

	private BuildFromScratch() {
	}

	@Override
	public Set<IContextualizedProduction> apply(IClassification classification) {
		Set<IContextualizedProduction> prods = buildProductions(classification);
		Set<IContextualizedProduction> reducedProds = ProductionSetReducer.reduce(prods);
		ProductionSetBuilder.productionSalienceSetter().setUp(classification).accept(reducedProds);
		return reducedProds;
	}

	public Set<IContextualizedProduction> buildProductions(IClassification classification) {
		InvertedTree<IConcept, IIsA> conceptTree = classification.asGraph();
		Set<IContextualizedProduction> productions = new HashSet<>();
		List<IConcept> topoOrderedConcepts = conceptTree.getTopologicalOrder();
		for (int i = 0; i < topoOrderedConcepts.size() - 1; i++) {
			IConcept iConcept = topoOrderedConcepts.get(i);
			ProductionBuilder builder = ProductionSetBuilder.productionBuilder().setUp(iConcept);
			for (int j = i + 1; j < topoOrderedConcepts.size(); j++) {
				IConcept jConcept = topoOrderedConcepts.get(j);
				if (conceptTree.isStrictLowerBoundOf(iConcept, jConcept)) {
					for (IDenotation source : iConcept.getDenotations()) {
						for (IDenotation target : jConcept.getDenotations()) {
							Set<IContextualizedProduction> ijDenotationsProds = builder.apply(source, target);
							productions.addAll(ijDenotationsProds);
						}
					}
				}
			}
		}
		return productions;
	}

}
