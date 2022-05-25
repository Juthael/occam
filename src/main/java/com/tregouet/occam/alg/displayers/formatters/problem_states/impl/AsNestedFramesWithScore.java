package com.tregouet.occam.alg.displayers.formatters.problem_states.impl;

import com.tregouet.occam.alg.displayers.formatters.problem_states.ProblemStateLabeller;
import com.tregouet.occam.data.problem_space.states.IRepresentation;

public class AsNestedFramesWithScore extends AsNestedFrames implements ProblemStateLabeller {

	private static final String nL = System.lineSeparator();

	public AsNestedFramesWithScore() {
	}

	@Override
	public String apply(IRepresentation representation) {
		return super.apply(representation) + nL + representation.score().toString();
	}

}
