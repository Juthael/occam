package com.tregouet.occam.alg.displayers.visualizers.descriptions.exhaustive;

import java.util.function.BiFunction;

import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.DifferentiaeLabeller;
import com.tregouet.occam.data.structures.representations.descriptions.IDescription;

public interface DescriptionViz extends BiFunction<IDescription, String, String> {

	public static DifferentiaeLabeller differentiaeLabeller() {
		return FormattersAbstractFactory.INSTANCE.getDifferentiaeLabeller();
	}

}
