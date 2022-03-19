package com.tregouet.occam.alg.representation_gen.transitions_gen;

import java.util.Set;

import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.representations.properties.transitions.IConceptTransition;
import com.tregouet.tree_finder.data.Tree;

public interface IConceptTransitionsBuilder {
	
	Set<IConceptTransition> buildApplicationsAndClosedInheritancesFrom(
			Tree<IPreconcept, IIsA> treeOfPreconcepts, Set<IContextualizedProduction> contextualizedProductions);
	
	Set<IConceptTransition> buildClosuresFrom(Set<IConceptTransition> transitions);
	
	Set<IConceptTransition> buildUnclosedInheritancesFrom(Tree<IPreconcept, IIsA> treeOfPreconcepts);
	
	Set<IConceptTransition> buildSpontaneousTransitionsFrom(Tree<IPreconcept, IIsA> treeOfPreconcepts);

}
