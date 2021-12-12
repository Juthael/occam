package com.tregouet.occam.alg.score_calc.concept_derivation_cost;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.tree_finder.data.Tree;

public interface IConceptDerivationCostCalculator {
	
	double costOf(IsA conceptDerivation);
	
	IConceptDerivationCostCalculator input(Tree<IConcept, IsA> classificationTree);

}
