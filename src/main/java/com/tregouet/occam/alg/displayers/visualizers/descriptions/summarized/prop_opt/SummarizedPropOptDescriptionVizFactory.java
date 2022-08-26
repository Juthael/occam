package com.tregouet.occam.alg.displayers.visualizers.descriptions.summarized.prop_opt;

import com.tregouet.occam.alg.displayers.visualizers.descriptions.summarized.prop_opt.impl.UseDifferentiaePropOptSummarizer;

public class SummarizedPropOptDescriptionVizFactory {
	
	public static final SummarizedPropOptDescriptionVizFactory INSTANCE = new SummarizedPropOptDescriptionVizFactory();
	
	private SummarizedPropOptDescriptionVizFactory() {
	}
	
	public SummarizedPropOptDescriptionViz apply(SummarizedPropOptDescriptionVizStrategy strategy) {
		switch (strategy) {
		case USE_DIFF_PROP_OPT_SUMMARIZER : 
			return new UseDifferentiaePropOptSummarizer();
		default : 
			return null;
		}
	}

}
