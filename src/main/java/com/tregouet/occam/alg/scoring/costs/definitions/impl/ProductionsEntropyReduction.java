package com.tregouet.occam.alg.scoring.costs.definitions.impl;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.tregouet.occam.alg.scoring.costs.definitions.IDefinitionCoster;
import com.tregouet.occam.data.automata.machines.IAutomaton;
import com.tregouet.occam.data.automata.machines.descriptions.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.automata.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.tree_finder.data.Tree;

public class ProductionsEntropyReduction implements IDefinitionCoster {

	public static final ProductionsEntropyReduction INSTANCE = new ProductionsEntropyReduction();
	private int[] topoOrderedStateIDs = null;
	private Double[][] entropyReductionMatrix = null;
	private IGenusDifferentiaDefinition costed = null;
	
	private ProductionsEntropyReduction() {
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
		int nbOfProperties = 0;
		for (IConjunctiveTransition conjTransition : costed.getDifferentiae())
			nbOfProperties += conjTransition.howManyBasicProductions();
		costed.setCost(((double) nbOfProperties) * entropyReduction); 
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
