package com.tregouet.occam.alg.builders.similarity_assessor.impl;

import java.util.List;
import java.util.Map;

import org.jgrapht.alg.util.UnorderedPair;

import com.tregouet.occam.alg.builders.similarity_assessor.SimAssessorSetter;
import com.tregouet.occam.data.modules.similarity.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;

public abstract class ASimAssessorSetter implements SimAssessorSetter {
	
	protected List<IContextObject> context;
	protected IConceptLattice conceptLattice;
	protected Map<UnorderedPair<Integer, Integer>, IRepresentation> dichotomies;
	protected Map<UnorderedPair<Integer, Integer>, IRepresentation> differences;
	protected ISimilarityMetrics similarityMetrics;
	
	public ASimAssessorSetter() {
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
	
	@Override
	public SimAssessorSetter accept(List<IContextObject> context) {
		this.context = context;
		conceptLattice = SimAssessorSetter.conceptLatticeBuilder().apply(context);
		dichotomies = buildDichotomies();
		differences = buildDifferences();
		similarityMetrics = buildSimilarityMetrics();
		return this;
	}
	
	protected abstract Map<UnorderedPair<Integer, Integer>, IRepresentation> buildDichotomies();
	
	protected abstract Map<UnorderedPair<Integer, Integer>, IRepresentation> buildDifferences();
	
	protected abstract ISimilarityMetrics buildSimilarityMetrics();

}
