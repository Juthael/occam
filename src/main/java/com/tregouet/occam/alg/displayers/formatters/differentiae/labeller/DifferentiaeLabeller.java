package com.tregouet.occam.alg.displayers.formatters.differentiae.labeller;

import java.util.function.Function;

import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.properties.PropertyLabeller;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;

public interface DifferentiaeLabeller extends Function<ADifferentiae, String> {

	public static PropertyLabeller getPropertyDisplayer() {
		return FormattersAbstractFactory.INSTANCE.getPropertyDisplayer();
	}

}
