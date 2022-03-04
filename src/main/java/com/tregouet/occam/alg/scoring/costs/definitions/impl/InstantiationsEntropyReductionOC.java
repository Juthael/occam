package com.tregouet.occam.alg.scoring.costs.definitions.impl;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.tregouet.occam.alg.scoring.costs.definitions.IDefinitionCoster;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.descriptions.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transition_rules.IConjunctiveTransition;
import com.tregouet.occam.data.denotations.IConcept;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.tree_finder.data.Tree;

public class InstantiationsEntropyReductionOC implements IDefinitionCoster {

	public static final InstantiationsEntropyReductionOC INSTANCE = new InstantiationsEntropyReductionOC();
	private int[] topoOrderedStateIDs = null;
	private Double[][] entropyReductionMatrix = null;
	private IGenusDifferentiaDefinition costed = null;
	
	private InstantiationsEntropyReductionOC() {
	}

	@Override
	public void setCost() {
		IState speciesState = costed.getSpeciesState();
		IState genusState = costed.getGenusState();
		double entropyReduction;
		if (genusState.getStateType() == IState.OC_STATE) {
			entropyReduction = 1.0;
		}
		else {
			int speciesIdx = ArrayUtils.indexOf(topoOrderedStateIDs, speciesState.iD());
			int genusIdx = ArrayUtils.indexOf(topoOrderedStateIDs, genusState.iD());
			entropyReduction = entropyReductionMatrix[speciesIdx][genusIdx];
		}
		int nbOfInstantiatedVariables = 0;
		for (IConjunctiveTransition conjTransition : costed.getDifferentiae())
			nbOfInstantiatedVariables += conjTransition.howManyInstantiatedVariables();
		costed.setCost(((double) nbOfInstantiatedVariables) * entropyReduction); 
	}

	@Override
	public IDefinitionCoster input(IGenusDifferentiaDefinition costed) {
		this.costed = costed;
		return this;
	}
	
	@Override
	public void setCosterParameters(IAutomaton automaton) {
		Tree<IConcept, IIsA> treeOfDenotationSets = automaton.getTreeOfDenotationSets();
		List<IConcept> topoOrderedDenotationSets = treeOfDenotationSets.getTopologicalOrder();
		int cardinal = topoOrderedDenotationSets.size();
		topoOrderedStateIDs = new int[cardinal];
		for (int i = 0 ; i < cardinal ; i++) {
			topoOrderedStateIDs[i] = topoOrderedDenotationSets.get(i).getID();
		}
		entropyReductionMatrix = treeOfDenotationSets.getEntropyReductionMatrix();
	}	

}
