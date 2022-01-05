package com.tregouet.occam.alg.scoring.costs.definitions.impl;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.tregouet.occam.alg.scoring.costs.definitions.IDefinitionCoster;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.descriptions.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIsA;
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
		IState species = costed.getSpecies();
		IState genus = costed.getGenus();
		double entropyReduction;
		if (genus.getStateType() == IState.OC_STATE) {
			entropyReduction = 1.0;
		}
		else {
			int speciesIdx = ArrayUtils.indexOf(topoOrderedStateIDs, species.getStateID());
			int genusIdx = ArrayUtils.indexOf(topoOrderedStateIDs, genus.getStateID());
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
	public void setCosterParameters(ITransitionFunction transitionFunction) {
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
