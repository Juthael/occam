package com.tregouet.occam.alg.displayers.visualizers.descriptions.summarized;

import com.tregouet.occam.alg.displayers.visualizers.descriptions.summarized.impl.UseDifferentiaeSummarizer;

public class SummarizedDescriptionVizFactory {
	
	public static final SummarizedDescriptionVizFactory INSTANCE = new SummarizedDescriptionVizFactory();
	
	private SummarizedDescriptionVizFactory() {
	}
	
	public SummarizedDescriptionViz apply(SummarizedDescriptionVizStrategy strategy) {
		switch (strategy) {
		case USE_DIFF_SUMMARIZER : 
			return UseDifferentiaeSummarizer.INSTANCE;
		default : 
			return null;
		}
	}

}
