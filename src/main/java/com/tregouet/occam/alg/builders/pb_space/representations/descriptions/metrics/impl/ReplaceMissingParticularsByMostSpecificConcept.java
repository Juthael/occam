package com.tregouet.occam.alg.builders.pb_space.representations.descriptions.metrics.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import com.tregouet.occam.alg.builders.pb_space.representations.descriptions.metrics.SimilarityMetricsBuilder;
import com.tregouet.occam.data.representations.descriptions.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.representations.descriptions.metrics.impl.SimilarityMetrics;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public class ReplaceMissingParticularsByMostSpecificConcept implements SimilarityMetricsBuilder {
	
	public static final ReplaceMissingParticularsByMostSpecificConcept INSTANCE = 
			new ReplaceMissingParticularsByMostSpecificConcept();
	
	@Override
	public ISimilarityMetrics apply(Tree<Integer, AbstractDifferentiae> partialDescriptionTree, 
			Map<Integer, Integer> particularID2MostSpecificConceptID) {
		TreeSet<Integer> particularIDs = new TreeSet<>(particularID2MostSpecificConceptID.keySet());
		Iterator<Integer> particularIte = particularIDs.iterator();
		int[] mostSpecificConcepts = new int[particularIDs.size()];
		int arrayIdx = 0;
		while (particularIte.hasNext())
			mostSpecificConcepts[arrayIdx++] = particularID2MostSpecificConceptID.get(particularIte.next());
		return new SimilarityMetrics(mostSpecificConcepts, 
				SimilarityMetricsBuilder.pairSimilarityScorer().setAsContext(partialDescriptionTree),
				SimilarityMetricsBuilder.asymmetricalSimilarityScorer().setAsContext(partialDescriptionTree));
	}



}


