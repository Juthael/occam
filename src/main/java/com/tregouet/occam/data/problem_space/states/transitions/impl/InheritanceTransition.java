package com.tregouet.occam.data.problem_space.states.transitions.impl;

import java.util.Arrays;
import java.util.HashSet;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.impl.Application;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;
import com.tregouet.occam.data.problem_space.states.productions.impl.EpsilonProd;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.TransitionType;
import com.tregouet.occam.data.problem_space.states.transitions.dimensions.Nothing;

public class InheritanceTransition extends ConceptTransition implements IConceptTransition {

	// for inheritance of unclosed denotations.
	public InheritanceTransition(int inputStateID, int outputStateID, IDenotation speciesDenotation, IDenotation genusDenotation, AVariable variable) {
		super(new ConceptTransitionIC(inputStateID, 
				new Application(
						genusDenotation, 
						new HashSet<IProduction>(Arrays.asList(new IProduction[] {EpsilonProd.INSTANCE})), 
						speciesDenotation), 
				variable),
				new ConceptTransitionOIC(outputStateID, Arrays.asList(new AVariable[] { variable })));
	}

	// for inheritance of closed denotations
	public InheritanceTransition(int inputStateID, int outputStateID) {
		super(
				new ConceptTransitionIC(
						inputStateID,
						new Application(
								null,
								new HashSet<IProduction>(Arrays.asList(new IProduction[] {EpsilonProd.INSTANCE})),
								null),
						Nothing.INSTANCE),
				new ConceptTransitionOIC(outputStateID, Arrays.asList(new AVariable[] { Nothing.INSTANCE })));
	}

	@Override
	public TransitionType type() {
		return TransitionType.INHERITANCE;
	}

}
