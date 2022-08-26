package com.tregouet.occam.alg.displayers.visualizers.descriptions.summarized.weight_opt;

import java.util.List;
import java.util.Map;

import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.summarized.weight_opt_summarizer.DifferentiaeWeightOptSummarizer;
import com.tregouet.occam.alg.displayers.visualizers.descriptions.DescriptionViz;

public interface SummarizedWeightOptDescriptionViz extends DescriptionViz {
	
	@Override
	SummarizedWeightOptDescriptionViz setUp(Map<Integer, List<Integer>> conceptID2ExtentIDs);
	
	public static DifferentiaeWeightOptSummarizer differentiaeWeightOptSummarizer() {
		return FormattersAbstractFactory.INSTANCE.getDifferentiaeWeightOptSummarizer();
	}

}
