package com.tregouet.occam.data.concepts.impl;

import com.tregouet.occam.alg.cost_calc.concept_derivation_cost.ConceptDerivationCostStrategy;
import com.tregouet.occam.alg.cost_calc.concept_derivation_cost.IConceptDerivationCostCalculator;
import com.tregouet.occam.alg.cost_calc.concept_derivation_cost.impl.DerivationCostCalculatorFactory;
import com.tregouet.occam.data.concepts.IClassification;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.tree_finder.data.Tree;

public class Classification implements IClassification {

	private final Tree<IConcept, IsA> classificationTree;
	private final IConceptDerivationCostCalculator costCalc;
	
	public Classification(Tree<IConcept, IsA> classificationTree, ConceptDerivationCostStrategy costStrategy) {
		this.classificationTree = classificationTree;
		costCalc = DerivationCostCalculatorFactory.apply(costStrategy).input(classificationTree);
	}

	@Override
	public Tree<IConcept, IsA> getClassificationTree() {
		return classificationTree;
	}

	@Override
	public double getCostOf(IsA derivation) {
		return costCalc.costOf(derivation);
	}

}
