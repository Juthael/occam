package com.tregouet.occam.data.modules.similarity.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.alg.util.UnorderedPair;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.similarity_assessor.SimAssessorSetter;
import com.tregouet.occam.data.modules.similarity.ISimilarityAssessor;
import com.tregouet.occam.data.modules.similarity.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.representations.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.data.representations.classifications.concepts.IIsA;

public class SimilarityAssessor implements ISimilarityAssessor {
	
	private List<IContextObject> context = null;
	private IConceptLattice conceptLattice = null;
	private Map<UnorderedPair<Integer, Integer>, IRepresentation> dichotomies = null;
	private Map<UnorderedPair<Integer, Integer>, IRepresentation> differences = null;
	private ISimilarityMetrics similarityMetrics = null;
	private IRepresentation activeRepOfSimilarity = null;
	private IRepresentation activeRepOfDifferences = null;
	
	public SimilarityAssessor() {
	}

	@Override
	public DirectedAcyclicGraph<IConcept, IIsA> getLatticeOfConcepts() {
		return conceptLattice.getLatticeOfConcepts();
	}

	@Override
	public Boolean display(int particularID1, int particularID2) {
		UnorderedPair<Integer, Integer> pair = UnorderedPair.of(particularID1, particularID2);
		IRepresentation dichotomy = dichotomies.get(pair);
		IRepresentation diff = differences.get(pair);
		if (dichotomy == null || diff == null)
			return null;
		if (dichotomy.equals(activeRepOfSimilarity))
			return false;
		activeRepOfSimilarity = dichotomy;
		activeRepOfDifferences = diff;
		return true;
	}

	@Override
	public IRepresentation getActiveRepresentationOfSimilarity() {
		return activeRepOfSimilarity;
	}

	@Override
	public IRepresentation getActiveRepresentationOfDifferences() {
		return activeRepOfDifferences;
	}

	@Override
	public List<IContextObject> getContext() {
		return context;
	}

	@Override
	public double[][] getSimilarityMatrix() {
		return similarityMetrics.getSimilarityMatrix();
	}

	@Override
	public double[][] getAsymmetricalSimilarityMatrix() {
		return similarityMetrics.getAsymmetricalSimilarityMatrix();
	}

	@Override
	public double[][] getDifferenceMatrix() {
		return similarityMetrics.getDifferenceMatrix();
	}

	@Override
	public double[] getTypicalityVector() {
		return similarityMetrics.getTypicalityVector();
	}

	@Override
	public ISimilarityAssessor process(Set<IContextObject> context) {
		// TODO Auto-generated method stub
		return null;
	}

}
