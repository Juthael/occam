package com.tregouet.occam.alg.displayers.formatters.transition_functions.transitions;

import java.util.function.Function;

import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.applications.ApplicationLabeller;
import com.tregouet.occam.data.problem_space.states.transitions.IConceptTransition;

public interface TransitionLabeller extends Function<IConceptTransition, String> {
	
	public static ApplicationLabeller getApplicationLabeller() {
		return FormattersAbstractFactory.INSTANCE.getApplicationLabeller();
	}

}
