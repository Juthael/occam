package com.tregouet.occam.alg.calculators.costs.transitions.impl;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.tregouet.occam.alg.calculators.costs.transitions.ITransitionCoster;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transitions.ITransition;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.tree_finder.data.Tree;

public class EntropyReduction implements ITransitionCoster {

	public static final EntropyReduction INSTANCE = new EntropyReduction();
	private int[] topoOrderedStateIDs = null;
	private Double[][] entropyReductionMatrix = null;
	private ITransition transition = null;
	
	public EntropyReduction() {
	}

	@Override
	public ITransitionCoster input(ITransition transition) {
		this.transition = transition;
		return this;
	}

	@Override
	public void setCost() {
		int speciesIdx = ArrayUtils.indexOf(topoOrderedStateIDs, transition.getOperatingState().getStateID());
		int genusIdx = ArrayUtils.indexOf(topoOrderedStateIDs, transition.getNextState().getStateID());
		double entropyReduction = entropyReductionMatrix[speciesIdx][genusIdx];
		double nbOfProperties = (double) setNbOfProperties();
		double transitionCost = nbOfProperties * entropyReduction;
		transition.setCost(transitionCost);
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
	
	private int setNbOfProperties() {
		int nbOfProperties = 0;
		if (transition instanceof IConjunctiveTransition) {
			List<ITransition> elementaryTransitions = ((IConjunctiveTransition) transition).getComponents();
			for (ITransition transition : elementaryTransitions) {
				if (!transition.isBlank() || transition.isReframer())
					nbOfProperties++;
			}
		}
		else if (!transition.isBlank() || transition.isReframer())
			nbOfProperties = 1;
		return nbOfProperties;
	}

}
