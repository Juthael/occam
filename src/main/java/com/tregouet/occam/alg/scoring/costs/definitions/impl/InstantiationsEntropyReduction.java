package com.tregouet.occam.alg.scoring.costs.definitions.impl;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.tregouet.occam.alg.scoring.costs.definitions.IDefinitionCoster;
import com.tregouet.occam.data.abstract_machines.automatons.IAutomaton;
import com.tregouet.occam.data.abstract_machines.automatons.descriptions.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.transition_rules.IConjunctiveTransition;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.tree_finder.data.Tree;

public class InstantiationsEntropyReduction implements IDefinitionCoster {

	public static final InstantiationsEntropyReduction INSTANCE = new InstantiationsEntropyReduction();
	private int[] topoOrderedStateIDs = null;
	private Double[][] entropyReductionMatrix = null;
	private IGenusDifferentiaDefinition costed = null;
	
	private InstantiationsEntropyReduction() {
	}

	@Override
	public IDefinitionCoster input(IGenusDifferentiaDefinition costed) {
		this.costed = costed;
		return this;
	}

	@Override
	public void setCost() {
		int speciesIdx = ArrayUtils.indexOf(topoOrderedStateIDs, costed.getSpeciesState().iD());
		int genusIdx = ArrayUtils.indexOf(topoOrderedStateIDs, costed.getGenusState().iD());
		double entropyReduction = entropyReductionMatrix[speciesIdx][genusIdx];
		int nbOfInstantiatedVariables = 0;
		for (IConjunctiveTransition conjTransition : costed.getDifferentiae())
			nbOfInstantiatedVariables += conjTransition.howManyInstantiatedVariables();
		costed.setCost(((double) nbOfInstantiatedVariables) * entropyReduction); 
	}
	
	@Override
	public void setCosterParameters(IAutomaton automaton) {
		Tree<IDenotationSet, IIsA> treeOfDenotationSets = automaton.getTreeOfDenotationSets();
		List<IDenotationSet> topoOrderedDenotationSets = treeOfDenotationSets.getTopologicalOrder();
		int cardinal = topoOrderedDenotationSets.size();
		topoOrderedStateIDs = new int[cardinal];
		for (int i = 0 ; i < cardinal ; i++) {
			topoOrderedStateIDs[i] = topoOrderedDenotationSets.get(i).getID();
		}
		entropyReductionMatrix = treeOfDenotationSets.getEntropyReductionMatrix();
	}		

}
