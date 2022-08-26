package com.tregouet.occam.alg.displayers.visualizers.descriptions.summarized.weight_opt;

import com.tregouet.occam.alg.displayers.visualizers.descriptions.summarized.weight_opt.impl.UseDifferentiaeWeightOptSummarizer;

public class SummarizedWeightOptDescriptionVizFactory {
	
	public static final SummarizedWeightOptDescriptionVizFactory INSTANCE = new SummarizedWeightOptDescriptionVizFactory();
	
	private SummarizedWeightOptDescriptionVizFactory() {
	}
	
	public SummarizedWeightOptDescriptionViz apply(SummarizedWeightOptDescriptionVizStrategy strategy) {
		switch (strategy) {
		case USE_DIFF_WEIGHT_OPT_SUMMARIZER : 
			return new UseDifferentiaeWeightOptSummarizer();
		default : 
			return null;
		}
	}

}
