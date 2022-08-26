package com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.summarized.prop_opt_summarizer;

import com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.summarized.prop_opt_summarizer.impl.NonRedundantPropOptSubset;

public class DifferentiaePropOptSummarizerFactory {
	
	public static final DifferentiaePropOptSummarizerFactory INSTANCE = new DifferentiaePropOptSummarizerFactory();
	
	private DifferentiaePropOptSummarizerFactory() {
	}
	
	public DifferentiaePropOptSummarizer apply(DifferentiaePropOptSummarizerStrategy strategy) {
		switch(strategy) {
		case NON_REDUNDANT_PROP_OPT_SUBSET : 
			return NonRedundantPropOptSubset.INSTANCE;
		default : 
			return null;
		}
	}

}
