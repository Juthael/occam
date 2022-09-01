package com.tregouet.occam.alg.displayers.visualizers.descriptions;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.descriptions.differentiae.DifferentiaeFormatter;
import com.tregouet.occam.alg.displayers.formatters.descriptions.genus.GenusFormatter;
import com.tregouet.occam.data.structures.representations.descriptions.IDescription;

public interface DescriptionViz extends BiFunction<IDescription, String, String> {

	DescriptionViz setUp(Map<Integer, List<Integer>> conceptID2ExtentIDs, DescriptionFormat format);

	public static DifferentiaeFormatter differentiaeFormatter() {
		return FormattersAbstractFactory.INSTANCE.getDifferentiaeFormatter();
	}

	public static GenusFormatter genusFormatter() {
		return FormattersAbstractFactory.INSTANCE.getGenusFormatter();
	}

}
