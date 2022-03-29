package com.tregouet.occam.alg.builders.representations.descriptions.metrics.impl;

import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import com.tregouet.occam.alg.builders.representations.descriptions.metrics.SimilarityMetricsBuilder;
import com.tregouet.occam.data.representations.descriptions.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.representations.descriptions.metrics.impl.SimilarityMetrics;
import com.tregouet.occam.data.representations.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public class DeferredMatricesInstantiation implements SimilarityMetricsBuilder {

	public static final DeferredMatricesInstantiation INSTANCE = new DeferredMatricesInstantiation();
	
	private DeferredMatricesInstantiation() {
	}
	
	@Override
	public ISimilarityMetrics apply(Tree<Integer, AbstractDifferentiae> descriptionTree) {
		List<Integer> topologicalOrder = descriptionTree.getTopologicalOrder();
		TreeSet<Integer> particulars = 
				new TreeSet<>((e1, e2) -> topologicalOrder.indexOf(e1) - topologicalOrder.indexOf(e2));
		particulars.addAll(descriptionTree.getLeaves());
		int nbOfParticulars = particulars.size();
		int[] particularArray = new int[nbOfParticulars];
		int arrayIdx = 0;
		Iterator<Integer> particularIte = particulars.iterator();
		while (particularIte.hasNext())
			particularArray[arrayIdx++] = particularIte.next();
		return new SimilarityMetrics(
				particularArray, 
				SimilarityMetricsBuilder.pairSimilarityScorer(), 
				SimilarityMetricsBuilder.asymmetricalSimilarityScorer());
	}

}
