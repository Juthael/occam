package com.tregouet.occam.alg.generators.representations.transitions_gen.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.alg.generators.representations.transitions_gen.IConceptTransitionsBuilder;
import com.tregouet.occam.alg.generators.representations.transitions_gen.ITransitionsConstructionManager;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.tree_finder.data.Tree;

public class TransitionsConstructionManager implements ITransitionsConstructionManager {

	
	private Tree<IPreconcept, IIsA> treeOfPreconcepts = null;
	private Set<IContextualizedProduction> unfilteredUnreducedProds = null;
	
	public TransitionsConstructionManager() {
	}
	
	@Override
	public ITransitionsConstructionManager input(Tree<IPreconcept, IIsA> treeOfPreconcepts,
			Set<IContextualizedProduction> unfilteredUnreducedProds) {
		this.treeOfPreconcepts = treeOfPreconcepts;
		this.unfilteredUnreducedProds = unfilteredUnreducedProds;
		return this;
	}

	@Override
	public Set<IConceptTransition> output() {
		Set<IConceptTransition> transitions = new HashSet<>();
		IConceptTransitionsBuilder bldr = ConceptTransitionsBuilder.INSTANCE;
		transitions.addAll(bldr.buildApplicationsAndClosedInheritancesFrom(treeOfPreconcepts, unfilteredUnreducedProds));
		transitions.addAll(bldr.buildClosuresFrom(transitions));
		transitions.addAll(bldr.buildUnclosedInheritancesFrom(treeOfPreconcepts));
		transitions.addAll(bldr.buildSpontaneousTransitionsFrom(treeOfPreconcepts));
		transitions.add(bldr.buildInitialTransition(treeOfPreconcepts));
		return transitions;
	}

}
