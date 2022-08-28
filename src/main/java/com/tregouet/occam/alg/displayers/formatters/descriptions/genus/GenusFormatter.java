package com.tregouet.occam.alg.displayers.formatters.descriptions.genus;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface GenusFormatter extends Function<Integer, String> {

	public GenusFormatter setUp(Map<Integer, List<Integer>> conceptID2ExtentIDs);

}
