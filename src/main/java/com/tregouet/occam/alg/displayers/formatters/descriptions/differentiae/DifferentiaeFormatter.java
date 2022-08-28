package com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae;

import java.util.function.BiFunction;

import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.properties.PropertyLabeller;
import com.tregouet.occam.alg.displayers.visualizers.descriptions.DescriptionFormat;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;

public interface DifferentiaeFormatter extends BiFunction<ADifferentiae, DescriptionFormat, String> {

	public static PropertyLabeller getPropertyDisplayer() {
		return FormattersAbstractFactory.INSTANCE.getPropertyLabeller();
	}

}
