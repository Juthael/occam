package com.tregouet.occam.alg.scoring.scores.similarity.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jgrapht.Graphs;

import com.tregouet.occam.alg.scoring.scores.similarity.ISimilarityScorer;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.functions.descriptions.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

public class DynamicFraming extends AbstractSimCalculator implements ISimilarityScorer {

	private Tree<IState, IGenusDifferentiaDefinition> porphyrianTree = null;
	private Map<Integer, IState> stateIDToState = null;
	
	public DynamicFraming() {
	}

	@Override
	public double howSimilar(int conceptID1, int conceptID2) {
		double genusDefinitionCost;
		double concept1DefinitionCost;
		double concept2DefinitionCost;
		if (conceptID1 == conceptID2)
			return 1.0;
		IState concept1 = stateIDToState.get(conceptID1);
		IState concept2 = stateIDToState.get(conceptID2);
		IState genus = Functions.supremum(porphyrianTree, concept1, concept2);
		genusDefinitionCost = getDefinitionCostOf(genus);
		if (genusDefinitionCost == 0.0)
			return 0.0;
		IState concept1AsGenusSpecies = null;
		IGenusDifferentiaDefinition concept1DefinitionAsGenusSpecies;
		IState concept2AsGenusSpecies = null;
		IGenusDifferentiaDefinition concept2DefinitionAsGenusSpecies;
		Iterator<IState> genusSpeciesIte = Graphs.predecessorListOf(porphyrianTree, genus).iterator();
		while (concept1AsGenusSpecies == null || concept2AsGenusSpecies == null) {
			IState genusSpecies = genusSpeciesIte.next();
			if (concept1.equals(genusSpecies) || porphyrianTree.isStrictLowerBoundOf(concept1, genusSpecies))
				concept1AsGenusSpecies = genusSpecies;
			else if (concept2.equals(genusSpecies) || porphyrianTree.isStrictLowerBoundOf(concept2, genusSpecies))
				concept2AsGenusSpecies = genusSpecies;
		}
		concept1DefinitionAsGenusSpecies = porphyrianTree.getEdge(concept1AsGenusSpecies, genus);
		concept2DefinitionAsGenusSpecies = porphyrianTree.getEdge(concept2AsGenusSpecies, genus);
		concept1DefinitionCost = concept1DefinitionAsGenusSpecies.getCost();
		concept2DefinitionCost = concept2DefinitionAsGenusSpecies.getCost();
		return genusDefinitionCost / (genusDefinitionCost + concept1DefinitionCost + concept2DefinitionCost);
	}

	@Override
	public double howSimilarTo(int conceptID1, int conceptID2) {
		double genusDefinitionCost;
		double concept1DefinitionCost;
		if (conceptID1 == conceptID2)
			return 1.0;
		IState concept1 = stateIDToState.get(conceptID1);
		IState concept2 = stateIDToState.get(conceptID2);
		IState genus = Functions.supremum(porphyrianTree, concept1, concept2);
		genusDefinitionCost = getDefinitionCostOf(genus);
		if (genusDefinitionCost == 0.0)
			return 0.0;
		IState concept1AsGenusSpecies = null;
		IGenusDifferentiaDefinition concept1DefinitionAsGenusSpecies;
		Iterator<IState> genusSpeciesIte = Graphs.predecessorListOf(porphyrianTree, genus).iterator();
		while (concept1AsGenusSpecies == null) {
			IState genusSpecies = genusSpeciesIte.next();
			if (concept1.equals(genusSpecies) || porphyrianTree.isStrictLowerBoundOf(concept1, genusSpecies))
				concept1AsGenusSpecies = genusSpecies;
		}
		concept1DefinitionAsGenusSpecies = porphyrianTree.getEdge(concept1AsGenusSpecies, genus);
		concept1DefinitionCost = concept1DefinitionAsGenusSpecies.getCost();
		return genusDefinitionCost / (genusDefinitionCost + concept1DefinitionCost);
	}
	
	@Override
	public ISimilarityScorer input(ITransitionFunction transitionFunction) {
		super.input(transitionFunction);
		porphyrianTree = transitionFunction.getPorphyrianTree();
		stateIDToState = new HashMap<>();
		for (IState state : transitionFunction.getStates())
			stateIDToState.put(state.getStateID(), state);
		transitionFunction.setSimilarityScorer(this);
		return this;
	}	
	
	private double getDefinitionCostOf(IState concept) {
		double transitionCost = 0.0;
		IState currConcept = concept;
		IState ontologicalCommitment = porphyrianTree.getRoot();
		while (!currConcept.equals(ontologicalCommitment)) {
			IGenusDifferentiaDefinition currConceptDefinition = 
					porphyrianTree.outgoingEdgesOf(currConcept).iterator().next();
			transitionCost += currConceptDefinition.getCost();
			currConcept = porphyrianTree.getEdgeTarget(currConceptDefinition);
		}
		return transitionCost;
	}

}
