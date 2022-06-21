package com.tregouet.occam.alg.displayers.formatters.problem_states.impl;

import com.ibm.icu.text.DecimalFormat;
import com.tregouet.occam.alg.displayers.formatters.problem_states.ProblemStateLabeller;
import com.tregouet.occam.data.problem_space.states.IRepresentation;

public class AsNestedFramesWithScore extends AsNestedFrames implements ProblemStateLabeller {

	private static final String nL = System.lineSeparator();
	private static final DecimalFormat df = new DecimalFormat("#.####");

	public AsNestedFramesWithScore() {
	}

	@Override
	public String apply(IRepresentation representation) {
		return super.apply(representation) + nL + round(representation.score());
	}
	

	private static String round(double nb) {
		return df.format(nb).toString();
	}	

}
