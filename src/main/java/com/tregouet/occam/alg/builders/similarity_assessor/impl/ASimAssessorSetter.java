package com.tregouet.occam.alg.builders.similarity_assessor.impl;

import java.util.List;
import java.util.Map;

import org.jgrapht.alg.util.UnorderedPair;

import com.tregouet.occam.alg.builders.similarity_assessor.SimAssessorSetter;
import com.tregouet.occam.data.modules.similarity.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.classifications.concepts.IContextObject;

public abstract class ASimAssessorSetter implements SimAssessorSetter {
	
	protected final List<IContextObject> context;
	protected final IConceptLattice conceptLattice;
	protected final Map<UnorderedPair<Integer, Integer>, IRepresentation> dichotomies;
	protected final Map<UnorderedPair<Integer, Integer>, IRepresentation> differences;
	protected final ISimilarityMetrics similarityMetrics;
	
	public ASimAssessorSetter(List<IContextObject> context) {
		this.context = context;
		conceptLattice = SimAssessorSetter.conceptLatticeBuilder().apply(context);
		dichotomies = buildDichotomies();
		differences = buildDifferences();
		similarityMetrics = buildSimilarityMetrics();
	}

	@Override
	public List<IContextObject> getContext() {
		return context;
	}

	@Override
	public IConceptLattice getConceptLattice() {
		return conceptLattice;
	}

	@Override
	public Map<UnorderedPair<Integer, Integer>, IRepresentation> getDichotomies() {
		return dichotomies;
	}

	@Override
	public Map<UnorderedPair<Integer, Integer>, IRepresentation> getDifferences() {
		return differences;
	}

	@Override
	public ISimilarityMetrics getSimilarityMetrics() {
		return similarityMetrics;
	}
	
	protected abstract Map<UnorderedPair<Integer, Integer>, IRepresentation> buildDichotomies();
	
	protected abstract Map<UnorderedPair<Integer, Integer>, IRepresentation> buildDifferences();
	
	protected abstract ISimilarityMetrics buildSimilarityMetrics();

}
