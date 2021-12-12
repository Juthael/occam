package com.tregouet.occam.alg.score_calc.concept_derivation_cost.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.score_calc.concept_derivation_cost.IConceptDerivationCostCalculator;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

public class EntropyReduction implements IConceptDerivationCostCalculator {

	private Map<IConcept, Integer> conceptToExtentSize;
	private Map<IsA, Double> derivationToCost;
	
	public EntropyReduction() {
	}

	private static double binaryLogarithm(double arg) {
		return Math.log10(arg)/Math.log10(2);
	}
	
	@Override
	public double costOf(IsA conceptDerivation) {
		return derivationToCost.get(conceptDerivation);
	}

	@Override
	public EntropyReduction input(Tree<IConcept, IsA> classificationTree) {
		conceptToExtentSize = new HashMap<>();
		derivationToCost = new HashMap<>();
		Set<IConcept> singletons = classificationTree.getLeaves();
		for (IConcept concept : classificationTree.vertexSet()) {
			conceptToExtentSize.put(
					concept, 
					Sets.intersection(Functions.lowerSet(classificationTree, concept), singletons).size());
		}
		for (IsA derivation : classificationTree.edgeSet()) {
			double genusExtentSize = 
					conceptToExtentSize.get(classificationTree.getEdgeTarget(derivation));
			double speciesExtentSize = 
					conceptToExtentSize.get(classificationTree.getEdgeSource(derivation));
			double derivationCost = -binaryLogarithm(speciesExtentSize / genusExtentSize);
			derivationToCost.put(derivation, derivationCost);
		}
		return this;
	}	

}
