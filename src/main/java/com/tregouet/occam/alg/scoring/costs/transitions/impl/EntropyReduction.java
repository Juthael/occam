package com.tregouet.occam.alg.scoring.costs.transitions.impl;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.tregouet.occam.alg.scoring.costs.transitions.ITransitionCoster;
import com.tregouet.occam.data.automata.machines.IAutomaton;
import com.tregouet.occam.data.automata.transition_rules.ICostedTransition;
import com.tregouet.occam.data.denotations.IPreconcept;
import com.tregouet.occam.data.denotations.IIsA;
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
		int speciesIdx = ArrayUtils.indexOf(topoOrderedStateIDs, transition.getInputState().iD());
		int genusIdx = ArrayUtils.indexOf(topoOrderedStateIDs, transition.getOutputState().iD());
		double entropyReduction = entropyReductionMatrix[speciesIdx][genusIdx];
		transition.setCost(entropyReduction);
	}

	@Override
	public void setCosterParameters(IAutomaton automaton) {
		Tree<IPreconcept, IIsA> treeOfDenotations = automaton.getTreeOfDenotationSets();
		List<IPreconcept> topoOrderedDenotations = treeOfDenotations.getTopologicalOrder();
		int cardinal = topoOrderedDenotations.size();
		topoOrderedStateIDs = new int[cardinal];
		for (int i = 0 ; i < cardinal ; i++) {
			topoOrderedStateIDs[i] = topoOrderedDenotations.get(i).getID();
		}
		entropyReductionMatrix = treeOfDenotations.getEntropyReductionMatrix();
	}

}
