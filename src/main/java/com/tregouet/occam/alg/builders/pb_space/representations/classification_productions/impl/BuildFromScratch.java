package com.tregouet.occam.alg.builders.pb_space.representations.classification_productions.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.classification_productions.ClassificationProductionSetBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.classification_productions.utils.ProductionSetReducer;
import com.tregouet.occam.alg.builders.pb_space.representations.lattice_productions.ProductionBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.lattice_productions.from_denotations.ProdBuilderFromDenotations;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;
import com.tregouet.occam.data.problem_space.states.productions.Salience;
import com.tregouet.occam.data.problem_space.states.productions.impl.ClassificationProductions;
import com.tregouet.tree_finder.data.InvertedTree;

public class BuildFromScratch implements ClassificationProductionSetBuilder {

	public static final BuildFromScratch INSTANCE = new BuildFromScratch();
	
	private BuildFromScratch() {
	}
	
	@Override
	public ClassificationProductions apply(IClassification classification) {
		Set<IContextualizedProduction> prods = buildProductions(classification);
		Set<IContextualizedProduction> reducedProds = ProductionSetReducer.reduce(prods);
		Map<IContextualizedProduction, Salience> prod2Salience = 
				ClassificationProductionSetBuilder.productionSalienceMapper().apply(classification, reducedProds);
		return new ClassificationProductions(prod2Salience);
	}

	@Override
	public ClassificationProductionSetBuilder setUp(Set<IContextualizedProduction> latticeProductions) {
		return this;
	}
	
	public Set<IContextualizedProduction> buildProductions(IClassification classification) {
		InvertedTree<IConcept, IIsA> conceptTree = classification.asGraph();
		Set<IContextualizedProduction> productions = new HashSet<>();
		List<IConcept> topoOrderedConcepts = conceptTree.getTopologicalOrder();
		for (int i = 0; i < topoOrderedConcepts.size() - 1; i++) {
			IConcept iConcept = topoOrderedConcepts.get(i);
			ProdBuilderFromDenotations builder = ProductionBuilder.prodBuilderFromDenotations().setUp(iConcept);
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
