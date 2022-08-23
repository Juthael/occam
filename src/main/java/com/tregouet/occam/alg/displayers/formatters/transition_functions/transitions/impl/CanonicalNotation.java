package com.tregouet.occam.alg.displayers.formatters.transition_functions.transitions.impl;

import java.util.Iterator;

import com.tregouet.occam.alg.displayers.formatters.transition_functions.transitions.TransitionLabeller;
import com.tregouet.occam.data.structures.lambda_terms.IBindings;
import com.tregouet.occam.data.structures.representations.transitions.IConceptTransition;

public class CanonicalNotation implements TransitionLabeller {

	public static final CanonicalNotation INSTANCE = new CanonicalNotation();

	private CanonicalNotation() {
	}

	@Override
	public String apply(IConceptTransition transition) {
		StringBuilder sB = new StringBuilder();
		sB.append(TransitionLabeller.getAbstrAppLabeller().apply(transition.getInputConfiguration().getInputSymbol()))
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
