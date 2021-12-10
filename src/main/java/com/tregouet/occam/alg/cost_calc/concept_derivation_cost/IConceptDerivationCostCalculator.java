package com.tregouet.occam.alg.cost_calc.concept_derivation_cost;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.tree_finder.data.Tree;

public interface IConceptDerivationCostCalculator {
	
	IConceptDerivationCostCalculator input(Tree<IConcept, IsA> classificationTree);
	
	double costOf(IsA conceptDerivation);

}
