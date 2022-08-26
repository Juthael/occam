package com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.summarized.weight_opt_summarizer;

import com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.summarized.weight_opt_summarizer.impl.NonRedundantWeightOptimalSubset;

public class DifferentiaeWeightOptSummarizerFactory {
	
	public static final DifferentiaeWeightOptSummarizerFactory INSTANCE = new DifferentiaeWeightOptSummarizerFactory();
	
	private DifferentiaeWeightOptSummarizerFactory() {
	}
	
	public DifferentiaeWeightOptSummarizer apply(DifferentiaeWeightOptSummarizerStrategy strategy) {
		switch(strategy) {
		case NON_REDUNDANT_WEIGHT_OPTIMAL_SUBSET : 
			return NonRedundantWeightOptimalSubset.INSTANCE;
		default : 
			return null;
		}
	}

}
