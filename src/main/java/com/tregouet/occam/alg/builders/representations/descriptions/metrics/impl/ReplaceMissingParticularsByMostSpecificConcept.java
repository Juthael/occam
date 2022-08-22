package com.tregouet.occam.alg.builders.representations.descriptions.metrics.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import com.tregouet.occam.alg.builders.representations.descriptions.metrics.RelativeSimilarityMetricsBuilder;
import com.tregouet.occam.data.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.occam.data.representations.descriptions.metrics.IRelativeSimilarityMetrics;
import com.tregouet.occam.data.representations.descriptions.metrics.impl.RelativeSimilarityMetrics;
import com.tregouet.tree_finder.data.Tree;

public class ReplaceMissingParticularsByMostSpecificConcept implements RelativeSimilarityMetricsBuilder {

	public static final ReplaceMissingParticularsByMostSpecificConcept INSTANCE =
			new ReplaceMissingParticularsByMostSpecificConcept();

	@Override
	public IRelativeSimilarityMetrics apply(Tree<Integer, ADifferentiae> partialDescriptionTree,
			Map<Integer, Integer> particularID2MostSpecificConceptID) {
		TreeSet<Integer> particularIDs = new TreeSet<>(particularID2MostSpecificConceptID.keySet());
		Iterator<Integer> particularIte = particularIDs.iterator();
		int[] mostSpecificConcepts = new int[particularIDs.size()];
		int arrayIdx = 0;
		while (particularIte.hasNext())
			mostSpecificConcepts[arrayIdx++] = particularID2MostSpecificConceptID.get(particularIte.next());
		return new RelativeSimilarityMetrics(mostSpecificConcepts,
				RelativeSimilarityMetricsBuilder.relativePairSimilarityScorer().setAsContext(partialDescriptionTree),
				RelativeSimilarityMetricsBuilder.relativeAsymmetricalSimilarityScorer().setAsContext(partialDescriptionTree));
	}



}


