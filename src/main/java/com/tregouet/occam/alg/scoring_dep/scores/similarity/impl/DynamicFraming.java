package com.tregouet.occam.alg.scoring_dep.scores.similarity.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jgrapht.Graphs;

import com.tregouet.occam.alg.scoring_dep.scores.similarity.ISimilarityScorer;
import com.tregouet.occam.data.logical_structures.automata.IAutomaton;
import com.tregouet.occam.data.logical_structures.automata.machines.deprec.IGenusDifferentiaDefinition_dep;
import com.tregouet.occam.data.logical_structures.automata.states.IState;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.utils.Functions;

public class DynamicFraming extends AbstractSimCalculator implements ISimilarityScorer {

	private InvertedTree<IState, IGenusDifferentiaDefinition_dep> porphyrianTree = null;
	private Map<Integer, IState> stateIDToState = null;
	
	public DynamicFraming() {
	}

	@Override
	public double howSimilar(int cncptID1, int cncptID2) {
		double genusDefinitionCost;
		double cncpt1DefinitionCost;
		double cncpt2DefinitionCost;
		if (cncptID1 == cncptID2)
			return 1.0;
		IState state1 = stateIDToState.get(cncptID1);
		IState state2 = stateIDToState.get(cncptID2);
		IState genusState = Functions.supremum(porphyrianTree, state1, state2);
		genusDefinitionCost = getDefinitionCostOf(genusState);
		if (genusDefinitionCost == 0.0)
			return 0.0;
		IState state1AsGenusSpecies = null;
		IGenusDifferentiaDefinition_dep cncpt1DefinitionAsGenusSpecies;
		IState state2AsGenusSpecies = null;
		IGenusDifferentiaDefinition_dep cncpt2DefinitionAsGenusSpecies;
		Iterator<IState> genusSpeciesStateIte = Graphs.predecessorListOf(porphyrianTree, genusState).iterator();
		while (state1AsGenusSpecies == null || state2AsGenusSpecies == null) {
			IState genusSpeciesState = genusSpeciesStateIte.next();
			if (state1.equals(genusSpeciesState) || porphyrianTree.isStrictLowerBoundOf(state1, genusSpeciesState))
				state1AsGenusSpecies = genusSpeciesState;
			else if (state2.equals(genusSpeciesState) || porphyrianTree.isStrictLowerBoundOf(state2, genusSpeciesState))
				state2AsGenusSpecies = genusSpeciesState;
		}
		cncpt1DefinitionAsGenusSpecies = porphyrianTree.getEdge(state1AsGenusSpecies, genusState);
		cncpt2DefinitionAsGenusSpecies = porphyrianTree.getEdge(state2AsGenusSpecies, genusState);
		cncpt1DefinitionCost = cncpt1DefinitionAsGenusSpecies.getCost();
		cncpt2DefinitionCost = cncpt2DefinitionAsGenusSpecies.getCost();
		return genusDefinitionCost / (genusDefinitionCost + cncpt1DefinitionCost + cncpt2DefinitionCost);
	}

	@Override
	public double howSimilarTo(int cncptID1, int cncptID2) {
		double genusDefinitionCost;
		double cncpt1DefinitionCost;
		if (cncptID1 == cncptID2)
			return 1.0;
		IState state1 = stateIDToState.get(cncptID1);
		IState state2 = stateIDToState.get(cncptID2);
		IState genusState = Functions.supremum(porphyrianTree, state1, state2);
		genusDefinitionCost = getDefinitionCostOf(genusState);
		if (genusDefinitionCost == 0.0)
			return 0.0;
		IState cncpt1AsGenusSpecies = null;
		IGenusDifferentiaDefinition_dep cncpt1DefinitionAsGenusSpecies;
		Iterator<IState> genusSpeciesStateIte = Graphs.predecessorListOf(porphyrianTree, genusState).iterator();
		while (cncpt1AsGenusSpecies == null) {
			IState genusSpeciesState = genusSpeciesStateIte.next();
			if (state1.equals(genusSpeciesState) || porphyrianTree.isStrictLowerBoundOf(state1, genusSpeciesState))
				cncpt1AsGenusSpecies = genusSpeciesState;
		}
		cncpt1DefinitionAsGenusSpecies = porphyrianTree.getEdge(cncpt1AsGenusSpecies, genusState);
		cncpt1DefinitionCost = cncpt1DefinitionAsGenusSpecies.getCost();
		return genusDefinitionCost / (genusDefinitionCost + cncpt1DefinitionCost);
	}
	
	@Override
	public ISimilarityScorer input(IAutomaton automaton) {
		super.input(automaton);
		porphyrianTree = automaton.getPorphyrianTree();
		stateIDToState = new HashMap<>();
		for (IState state : automaton.getStates())
			stateIDToState.put(state.iD(), state);
		automaton.setSimilarityScorer(this);
		return this;
	}	
	
	private double getDefinitionCostOf(IState cncptState) {
		double transitionCost = 0.0;
		IState currCncptState = cncptState;
		IState ontologicalCommitment = porphyrianTree.getRoot();
		while (!currCncptState.equals(ontologicalCommitment)) {
			IGenusDifferentiaDefinition_dep currCncptDefinition = 
					porphyrianTree.outgoingEdgesOf(currCncptState).iterator().next();
			transitionCost += currCncptDefinition.getCost();
			currCncptState = porphyrianTree.getEdgeTarget(currCncptDefinition);
		}
		return transitionCost;
	}

}
