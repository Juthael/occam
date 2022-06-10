package com.tregouet.occam.data.problem_space.states.transitions.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.impl.Everything;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.impl.WhatIsThere;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.impl.Application;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;
import com.tregouet.occam.data.problem_space.states.productions.impl.OmegaProd;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;
import com.tregouet.occam.data.problem_space.states.transitions.TransitionType;
import com.tregouet.occam.data.problem_space.states.transitions.dimensions.Nothing;
import com.tregouet.occam.data.problem_space.states.transitions.dimensions.This;

public class InitialTransition extends ConceptTransition implements IConceptTransition {

	public InitialTransition(Everything everything) {
		super(
				new ConceptTransitionIC(
						WhatIsThere.INSTANCE.iD(),
						new Application(
								null, 
								new HashSet<IProduction>(Arrays.asList(new IProduction[] {OmegaProd.INSTANCE})), 
								null),
				Nothing.INSTANCE),
				new ConceptTransitionOIC(everything.iD(),
						new ArrayList<>(Arrays.asList(new AVariable[] { Nothing.INSTANCE, This.INSTANCE }))));
	}

	@Override
	public TransitionType type() {
		return TransitionType.INITIAL;
	}

}
