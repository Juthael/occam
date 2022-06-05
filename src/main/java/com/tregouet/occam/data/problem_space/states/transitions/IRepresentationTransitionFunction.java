package com.tregouet.occam.data.problem_space.states.transitions;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.logical_structures.automata.transition_functions.IPushdownAutomatonTF;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

public interface IRepresentationTransitionFunction extends
		IPushdownAutomatonTF<IContextualizedProduction, AVariable, IConceptTransitionIC, IConceptTransitionOIC, IConceptTransition> {
	
	DirectedAcyclicGraph<Integer, AConceptTransitionSet> asGraph();

}
