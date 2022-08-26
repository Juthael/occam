package com.tregouet.occam.alg.displayers.visualizers.descriptions.exhaustive;

import java.util.List;
import java.util.Map;

import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.exhaustive.DifferentiaeExhaustiveLabeller;
import com.tregouet.occam.alg.displayers.visualizers.descriptions.DescriptionViz;

public interface BasicDescriptionViz extends DescriptionViz {
	
	@Override
	BasicDescriptionViz setUp(Map<Integer, List<Integer>> conceptID2ExtentIDs);

	public static DifferentiaeExhaustiveLabeller differentiaeExhaustiveLabeller() {
		return FormattersAbstractFactory.INSTANCE.getDifferentiaeExhaustiveLabeller();
	}

}
