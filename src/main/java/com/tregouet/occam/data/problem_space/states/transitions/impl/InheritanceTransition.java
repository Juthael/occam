package com.tregouet.occam.data.problem_space.states.transitions.impl;

import java.util.Arrays;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.TransitionType;
import com.tregouet.occam.data.problem_space.states.transitions.dimensions.Nothing;
import com.tregouet.occam.data.problem_space.states.transitions.productions.impl.ContextualizedEpsilonProd;

public class InheritanceTransition extends ConceptTransition implements IConceptTransition {

	// for inheritance of unclosed denotations.
	public InheritanceTransition(int inputStateID, int outputStateID, IDenotation speciesDenotation, IDenotation genusDenotation, AVariable variable) {
		super(new ConceptTransitionIC(inputStateID, new ContextualizedEpsilonProd(speciesDenotation, genusDenotation), variable),
				new ConceptTransitionOIC(outputStateID, Arrays.asList(new AVariable[] { variable })));
	}

	// for inheritance of closed denotations
	public InheritanceTransition(int inputStateID, int outputStateID) {
		super(new ConceptTransitionIC(inputStateID, new ContextualizedEpsilonProd(null, null), Nothing.INSTANCE),
				new ConceptTransitionOIC(outputStateID, Arrays.asList(new AVariable[] { Nothing.INSTANCE })));
	}

	@Override
	public TransitionType type() {
		return TransitionType.INHERITANCE;
	}

}
