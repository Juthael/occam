package com.tregouet.occam.alg.displayers.formatters.differentiae;

import java.util.function.Function;

import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.PropertyLabeller;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;

public interface DifferentiaeLabeller extends Function<ADifferentiae, String> {

	public static PropertyLabeller getPropertyDisplayer() {
		return FormattersAbstractFactory.INSTANCE.getPropertyDisplayer();
	}

}
