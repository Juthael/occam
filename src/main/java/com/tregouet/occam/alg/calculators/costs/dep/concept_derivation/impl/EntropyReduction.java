package com.tregouet.occam.alg.calculators.costs.dep.concept_derivation.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.calculators.costs.dep.concept_derivation.IConceptDerivationCostCalculator;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

public class EntropyReduction implements IConceptDerivationCostCalculator {

	protected Map<IConcept, Integer> conceptToExtentSize;
	protected Map<IIsA, Double> derivationToCost;
	
	public EntropyReduction() {
	}

	protected static double binaryLogarithm(double arg) {
		return Math.log10(arg)/Math.log10(2);
	}
	
	@Override
	public double costOf(IIsA conceptDerivation) {
		return derivationToCost.get(conceptDerivation);
	}

	@Override
	public EntropyReduction input(Tree<IConcept, IIsA> classificationTree) {
		conceptToExtentSize = new HashMap<>();
		derivationToCost = new HashMap<>();
		Set<IConcept> singletons = classificationTree.getLeaves();
		for (IConcept concept : classificationTree.vertexSet()) {
			conceptToExtentSize.put(
					concept, 
					Sets.intersection(Functions.lowerSet(classificationTree, concept), singletons).size());
		}
		for (IIsA derivation : classificationTree.edgeSet()) {
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
