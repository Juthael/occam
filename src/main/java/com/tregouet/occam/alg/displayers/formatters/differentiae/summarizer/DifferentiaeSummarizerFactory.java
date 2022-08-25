package com.tregouet.occam.alg.displayers.formatters.differentiae.summarizer;

import com.tregouet.occam.alg.displayers.formatters.differentiae.summarizer.impl.NonRedundantOptimalSubset;

public class DifferentiaeSummarizerFactory {
	
	public static final DifferentiaeSummarizerFactory INSTANCE = new DifferentiaeSummarizerFactory();
	
	private DifferentiaeSummarizerFactory() {
	}
	
	public DifferentiaeSummarizer apply(DifferentiaeSummarizerStrategy strategy) {
		switch(strategy) {
		case NON_REDUNDANT_OPTIMAL_SUBSET : 
			return NonRedundantOptimalSubset.INSTANCE;
		default : 
			return null;
		}
	}

}
