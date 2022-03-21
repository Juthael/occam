package com.tregouet.occam.alg.scoring_dep.costs.definitions.impl;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.tregouet.occam.alg.scoring_dep.costs.definitions.IDefinitionCoster;
import com.tregouet.occam.data.automata.IAutomaton;
import com.tregouet.occam.data.automata.machines.deprec.IGenusDifferentiaDefinition_dep;
import com.tregouet.occam.data.automata.transition_functions.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.tree_finder.data.Tree;

public class TransitionsEntropyReduction implements IDefinitionCoster {

	public static final TransitionsEntropyReduction INSTANCE = new TransitionsEntropyReduction();
	private int[] topoOrderedStateIDs = null;
	private Double[][] entropyReductionMatrix = null;
	private IGenusDifferentiaDefinition_dep costed = null;
	
	private TransitionsEntropyReduction() {
	}

	@Override
	public IDefinitionCoster input(IGenusDifferentiaDefinition_dep costed) {
		this.costed = costed;
		return this;
	}

	@Override
	public void setCost() {
		int speciesIdx = ArrayUtils.indexOf(topoOrderedStateIDs, costed.getSpeciesState().iD());
		int genusIdx = ArrayUtils.indexOf(topoOrderedStateIDs, costed.getGenusState().iD());
		double entropyReduction = entropyReductionMatrix[speciesIdx][genusIdx];
		int nbOfTransitions = 0;
		for (IConjunctiveTransition conjTransition : costed.getDifferentiae())
			nbOfTransitions += conjTransition.howManyOperators();
		costed.weigh(((double) nbOfTransitions) * entropyReduction); 
	}
	
	@Override
	public void setCosterParameters(IAutomaton automaton) {
		Tree<IPreconcept, IIsA> treeOfDenotationSets = automaton.getTreeOfDenotationSets();
		List<IPreconcept> topoOrderedDenotationSets = treeOfDenotationSets.getTopologicalOrder();
		int cardinal = topoOrderedDenotationSets.size();
		topoOrderedStateIDs = new int[cardinal];
		for (int i = 0 ; i < cardinal ; i++) {
			topoOrderedStateIDs[i] = topoOrderedDenotationSets.get(i).getID();
		}
		entropyReductionMatrix = treeOfDenotationSets.getEntropyReductionMatrix();
	}	

}
