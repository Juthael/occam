package com.tregouet.occam.data.problem_space.states.transitions.impl;

import java.util.Arrays;
import java.util.HashSet;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.impl.Application;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;
import com.tregouet.occam.data.problem_space.states.productions.impl.EpsilonProd;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.TransitionType;
import com.tregouet.occam.data.problem_space.states.transitions.dimensions.This;

public class SpontaneousTransition extends ConceptTransition implements IConceptTransition {

	public SpontaneousTransition(int inputStateID, int outputStateID) {
		super(
				new ConceptTransitionIC(
						inputStateID, 
						new Application(
								null,
								new HashSet<IProduction>(Arrays.asList(new IProduction[] {EpsilonProd.INSTANCE})),
								null), 
						This.INSTANCE),
				new ConceptTransitionOIC(outputStateID, Arrays.asList(new AVariable[] { This.INSTANCE })));
	}

	@Override
	public TransitionType type() {
		return TransitionType.SPONTANEOUS;
	}

}
