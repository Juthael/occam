package com.tregouet.occam.alg.displayers.visualizers.descriptions.summarized;

import java.util.function.BiFunction;

import com.tregouet.occam.alg.displayers.formatters.FormattersAbstractFactory;
import com.tregouet.occam.alg.displayers.formatters.differentiae.summarizer.DifferentiaeSummarizer;
import com.tregouet.occam.data.structures.representations.descriptions.IDescription;

public interface SummarizedDescriptionViz extends BiFunction<IDescription, String, String> {
	
	public static DifferentiaeSummarizer differentiaeSummarizer() {
		return FormattersAbstractFactory.INSTANCE.getDifferentiaeSummarizer();
	}

}
