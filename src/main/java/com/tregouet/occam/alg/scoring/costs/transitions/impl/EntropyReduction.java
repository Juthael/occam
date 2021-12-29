package com.tregouet.occam.alg.scoring.costs.transitions.impl;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.tregouet.occam.alg.scoring.costs.transitions.ITransitionCoster;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.transitions.ICostedTransition;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.tree_finder.data.Tree;

public class EntropyReduction implements ITransitionCoster {

	public static final EntropyReduction INSTANCE = new EntropyReduction();
	private int[] topoOrderedStateIDs = null;
	private Double[][] entropyReductionMatrix = null;
	private ICostedTransition transition = null;
	
	private EntropyReduction() {
	}

	@Override
	public ITransitionCoster input(ICostedTransition transition) {
		this.transition = transition;
		return this;
	}

	@Override
	public void setCost() {
		int speciesIdx = ArrayUtils.indexOf(topoOrderedStateIDs, transition.getOperatingState().getStateID());
		int genusIdx = ArrayUtils.indexOf(topoOrderedStateIDs, transition.getNextState().getStateID());
		double entropyReduction = entropyReductionMatrix[speciesIdx][genusIdx];
		transition.setCost(entropyReduction);
	}

	@Override
	public void setNewCosterParameters(ITransitionFunction transitionFunction) {
		Tree<IConcept, IIsA> treeOfConcepts = transitionFunction.getTreeOfConcepts();
		List<IConcept> topoOrderedConcepts = treeOfConcepts.getTopologicalOrder();
		int cardinal = topoOrderedConcepts.size();
		topoOrderedStateIDs = new int[cardinal];
		for (int i = 0 ; i < cardinal ; i++) {
			topoOrderedStateIDs[i] = topoOrderedConcepts.get(i).getID();
		}
		entropyReductionMatrix = treeOfConcepts.getEntropyReductionMatrix();
	}

}
