package com.tregouet.occam.alg.displayers.formatters.problem_states;

import java.util.function.Function;

import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.sortings.Sorting2StringConverter;
import com.tregouet.occam.data.problem_spaces.IProblemState;

public interface ProblemStateLabeller extends Function<IProblemState, String> {
	
	public static Sorting2StringConverter getSorting2StringConverter() {
		return FormattersAbstractFactory.INSTANCE.getSorting2StringConverter();
	}

}