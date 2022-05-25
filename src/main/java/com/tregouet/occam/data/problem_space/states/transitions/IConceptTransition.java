package com.tregouet.occam.data.problem_space.states.transitions;

import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.IPushdownAutomatonTransition;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.transitions.productions.IContextualizedProduction;

public interface IConceptTransition extends
		IPushdownAutomatonTransition<IContextualizedProduction, AVariable, IConceptTransitionIC, IConceptTransitionOIC> {

	Salience getSalience();

	void setSalience(Salience salience);

	@Override
	String toString();

	TransitionType type();

}
