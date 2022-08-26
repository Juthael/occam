package com.tregouet.occam.alg.displayers.formatters.differentiae.labeller.summarized.prop_opt_summarizer;

import java.util.function.Function;

import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.differentiae.properties.PropertyLabeller;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;

public interface DifferentiaePropOptSummarizer extends Function<ADifferentiae, String> {
	
	public static PropertyLabeller getPropertyDisplayer() {
		return FormattersAbstractFactory.INSTANCE.getPropertyLabeller();
	}

}
