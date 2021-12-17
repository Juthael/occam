package com.tregouet.occam.alg.calculators.costs.dep.concept_derivation;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.tree_finder.data.Tree;

public interface IConceptDerivationCostCalculator {
	
	double costOf(IIsA conceptDerivation);
	
	IConceptDerivationCostCalculator input(Tree<IConcept, IIsA> classificationTree);

}
