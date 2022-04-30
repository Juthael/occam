package com.tregouet.occam.alg.builders.problem_spaces.partial_representations.metrics.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import com.tregouet.occam.alg.builders.problem_spaces.partial_representations.metrics.SimilarityPartialMetricsBuilder;
import com.tregouet.occam.alg.builders.representations.descriptions.metrics.SimilarityMetricsBuilder;
import com.tregouet.occam.data.representations.descriptions.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.representations.descriptions.metrics.impl.SimilarityMetrics;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public class ReplaceMissingParticularsByMostSpecificGenus implements SimilarityPartialMetricsBuilder {
	
	public static final ReplaceMissingParticularsByMostSpecificGenus INSTANCE = 
			new ReplaceMissingParticularsByMostSpecificGenus();
	
	private ReplaceMissingParticularsByMostSpecificGenus() {
	}
	
	@Override
	public ISimilarityMetrics apply(Tree<Integer, AbstractDifferentiae> partialDescriptionTree, 
			Map<Integer, Integer> particularID2MostSpecificGenus) {
		TreeSet<Integer> particularIDs = new TreeSet<>(particularID2MostSpecificGenus.keySet());
		Iterator<Integer> particularIte = particularIDs.iterator();
		int[] mostSpecificGenera = new int[particularIDs.size()];
		int arrayIdx = 0;
		while (particularIte.hasNext())
			mostSpecificGenera[arrayIdx++] = particularID2MostSpecificGenus.get(particularIte.next());
		return new SimilarityMetrics(mostSpecificGenera, 
				SimilarityMetricsBuilder.pairSimilarityScorer().setAsContext(partialDescriptionTree),
				SimilarityMetricsBuilder.asymmetricalSimilarityScorer().setAsContext(partialDescriptionTree));
	}



}


