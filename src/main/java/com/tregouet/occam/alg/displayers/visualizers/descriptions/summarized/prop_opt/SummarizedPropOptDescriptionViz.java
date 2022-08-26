package com.tregouet.occam.alg.displayers.visualizers.descriptions.summarized.prop_opt;

import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.summarized.prop_opt_summarizer.DifferentiaePropOptSummarizer;
import com.tregouet.occam.alg.displayers.visualizers.descriptions.DescriptionViz;

public interface SummarizedPropOptDescriptionViz extends DescriptionViz {
	
	public static DifferentiaePropOptSummarizer differentiaePropOptSummarizer() {
		return FormattersAbstractFactory.INSTANCE.getDifferentiaePropOptSummarizer();
	}

}
