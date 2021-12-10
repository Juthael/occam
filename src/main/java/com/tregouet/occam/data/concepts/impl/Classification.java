package com.tregouet.occam.data.concepts.impl;

import com.tregouet.occam.alg.cost_calc.SimilarityCalculationStrategy;
import com.tregouet.occam.alg.cost_calc.concept_derivation_cost.ConceptDerivationCostStrategy;
import com.tregouet.occam.alg.cost_calc.concept_derivation_cost.IConceptDerivationCostCalculator;
import com.tregouet.occam.alg.cost_calc.concept_derivation_cost.impl.DerivationCostCalculatorFactory;
import com.tregouet.occam.alg.cost_calc.similarity_calc.ISimilarityCalculator;
import com.tregouet.occam.alg.cost_calc.similarity_calc.impl.SimilarityCalculatorFactory;
import com.tregouet.occam.data.concepts.IClassification;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.tree_finder.data.Tree;

public class Classification implements IClassification {

	private final Tree<IConcept, IsA> classificationTree;
	private final IConceptDerivationCostCalculator costCalc;
	private final ISimilarityCalculator simCalc;
	private final double coherenceScore;
	
	public Classification(Tree<IConcept, IsA> classificationTree, ConceptDerivationCostStrategy costStrategy, 
			SimilarityCalculationStrategy similarityStrategy) {
		this.classificationTree = classificationTree;
		costCalc = DerivationCostCalculatorFactory.apply(costStrategy).input(classificationTree);
		simCalc = SimilarityCalculatorFactory.apply(similarityStrategy).input(this);
		coherenceScore = simCalc.getCoherenceScore();
	}

	@Override
	public Tree<IConcept, IsA> getClassificationTree() {
		return classificationTree;
	}

	@Override
	public double getCostOf(IsA derivation) {
		return costCalc.costOf(derivation);
	}

	@Override
	public int compareTo(IClassification o) {
		if (o == this)
			return 0;
		double otherCoherenceScore = o.getCoherenceScore();
		if (coherenceScore > otherCoherenceScore)
			return 1;
		if (coherenceScore < otherCoherenceScore)
			return -1;
		int thisNbOfEdges = classificationTree.edgeSet().size();
		int otherNbOfEdges = o.getClassificationTree().edgeSet().size();
		if (thisNbOfEdges < otherNbOfEdges)
			return 1;
		if (thisNbOfEdges > otherNbOfEdges)
			return -1;
		return (System.identityHashCode(this) - System.identityHashCode(o));
	}

	@Override
	public double getCoherenceScore() {
		return coherenceScore;
	}

	@Override
	public ISimilarityCalculator getSimilarityCalculator() {
		return simCalc;
	}

}
