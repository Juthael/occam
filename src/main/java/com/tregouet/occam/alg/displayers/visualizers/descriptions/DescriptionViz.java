package com.tregouet.occam.alg.displayers.visualizers.descriptions;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import com.tregouet.occam.data.structures.representations.descriptions.IDescription;

public interface DescriptionViz extends BiFunction<IDescription, String, String> {
	
	DescriptionViz setUp(Map<Integer, List<Integer>> conceptID2ExtentIDs);

}
