package com.tregouet.occam.alg.calculators.costs.transitions.impl;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.calculators.costs.transitions.ITransitionCoster;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transitions.ITransition;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

public class EntropyReduction implements ITransitionCoster {

	public static final EntropyReduction INSTANCE = new EntropyReduction();
	private ITransitionFunction transitionFunction = null;
	protected Map<Integer, Integer> conceptIDToExtentSize = null;
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
		IState species = transition.getOperatingState();
		IState genus = transition.getNextState();
		int speciesExtent = species.

	}

	@Override
	public void setNewTransitionFunctionParameter(ITransitionFunction transitionFunction) {
		this.transitionFunction = transitionFunction;
		Tree<IConcept, IIsA> conceptTree = transitionFunction.getTreeOfConcepts();
		Set<IConcept> singletons = conceptTree.getLeaves();
		for (IConcept concept : conceptTree.vertexSet()) {
			conceptIDToExtentSize.put(
					concept.getID(), 
					Sets.intersection(Functions.lowerSet(conceptTree, concept), singletons).size());
		}
	}

}
