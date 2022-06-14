package com.tregouet.occam.alg.displayers.formatters.transition_functions.transitions.impl;

import java.util.Iterator;

import com.tregouet.occam.alg.displayers.formatters.transition_functions.transitions.TransitionLabeller;
import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;

public class CanonicalNotation implements TransitionLabeller {
	
	public static final CanonicalNotation INSTANCE = new CanonicalNotation();
	
	private CanonicalNotation() {
	}

	@Override
	public String apply(IConceptTransition transition) {
		StringBuilder sB = new StringBuilder();
		sB.append(TransitionLabeller.getComputationLabeller().apply(transition.getInputConfiguration().getInputSymbol()))
			.append(", ")
			.append(transition.getInputConfiguration().getStackSymbol().toString())
			.append(" → ");
		Iterator<IBindings> bindIte = transition.getOutputInternConfiguration().getPushedStackSymbols().iterator();
		while (bindIte.hasNext()) {
			sB.append(bindIte.next().toString());
			if (bindIte.hasNext())
				sB.append(", ");
		}
		return sB.toString();
	}

}
