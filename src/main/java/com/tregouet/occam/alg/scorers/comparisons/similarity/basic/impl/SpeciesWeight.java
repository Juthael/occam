package com.tregouet.occam.alg.scorers.comparisons.similarity.basic.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.jgrapht.alg.util.UnorderedPair;

import com.tregouet.occam.alg.scorers.comparisons.similarity.basic.SimilarityScorer;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public class SpeciesWeight implements SimilarityScorer {

	public static final SpeciesWeight INSTANCE = new SpeciesWeight();

	private SpeciesWeight() {
	}

	@Override
	public Double apply(UnorderedPair<Integer, Integer> comparedParticularIDs, IRepresentation dichotomisticRepresentation) {
		if (comparedParticularIDs.getFirst().equals(comparedParticularIDs.getSecond()))
			return null;
		List<Integer> speciesExtent = Arrays.asList(new Integer[] {comparedParticularIDs.getFirst(), comparedParticularIDs.getSecond()});
		speciesExtent.sort((x, y) -> Integer.compare(x, y));
		IClassification dichotomisticClassification = dichotomisticRepresentation.getClassification();
		Tree<Integer, ADifferentiae> dichotomisticDescGraph = dichotomisticRepresentation.getDescription().asGraph();
		Set<Integer> alternative = dichotomisticDescGraph.getLeaves();
		for (Integer leaf : alternative) {
			if (dichotomisticClassification.getExtentIDs(leaf).equals(speciesExtent))
				return dichotomisticDescGraph.incomingEdgeOf(leaf).weight();
		}
		return null; //never happens
	}

}
