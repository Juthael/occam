package com.tregouet.occam.alg.calculators.scores.similarity.impl;

import com.tregouet.occam.alg.calculators.scores.similarity.ISimilarityScorer;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.descriptions.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.tree_finder.data.Tree;

public class DynamicFramingSimCalculator extends AbstractSimCalculator implements ISimilarityScorer {

	private Tree<IState, IGenusDifferentiaDefinition> prophyrianTree = null;
	
	public DynamicFramingSimCalculator() {
		// TODO Auto-generated constructor stub
	}

	public DynamicFramingSimCalculator(ITransitionFunction transitionFunction) {
		super(transitionFunction);
		prophyrianTree = transitionFunction.getPorphyrianTree();
	}

	@Override
	public double howSimilar(int conceptID1, int conceptID2) {
	}

	@Override
	public double howSimilarTo(int conceptID1, int conceptID2) {
	}
	
	@Override
	public ISimilarityScorer input(ITransitionFunction transitionFunction) {
		super.input(transitionFunction);
		prophyrianTree = transitionFunction.getPorphyrianTree();
		return this;
	}	

}
