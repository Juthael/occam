package com.tregouet.occam.alg.scoring.costs.definitions.impl;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.tregouet.occam.alg.scoring.costs.definitions.IDefinitionCoster;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.descriptions.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIsA;
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
		int speciesIdx = ArrayUtils.indexOf(topoOrderedStateIDs, costed.getSpecies().getStateID());
		int genusIdx = ArrayUtils.indexOf(topoOrderedStateIDs, costed.getGenus().getStateID());
		double entropyReduction = entropyReductionMatrix[speciesIdx][genusIdx];
		int nbOfProperties = 0;
		for (IConjunctiveTransition conjTransition : costed.getDifferentiae())
			nbOfProperties += conjTransition.howManyProductions();
		costed.setCost(((double) nbOfProperties) * entropyReduction); 
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
