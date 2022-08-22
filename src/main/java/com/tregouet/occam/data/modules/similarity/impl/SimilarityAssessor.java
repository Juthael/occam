package com.tregouet.occam.data.modules.similarity.impl;

import java.util.List;
import java.util.Map;

import org.jgrapht.alg.util.UnorderedPair;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.modules.similarity.ISimilarityAssessor;
import com.tregouet.occam.data.modules.similarity.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.representations.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.data.representations.classifications.concepts.IIsA;

public class SimilarityAssessor implements ISimilarityAssessor {
	
	private final List<IContextObject> context;
	private final IConceptLattice conceptLattice;
	private final Map<UnorderedPair<Integer, Integer>, IRepresentation> dichotomies;
	private final Map<UnorderedPair<Integer, Integer>, IRepresentation> differences;
	private final ISimilarityMetrics similarityMetrics;
	private IRepresentation activeRepOfSimilarity;
	private IRepresentation activeRepOfDifferences;
	
	public SimilarityAssessor() {
		
	}

	@Override
	public DirectedAcyclicGraph<IConcept, IIsA> getLatticeOfConcepts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean display(int particularID1, int particularID2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRepresentation getActiveRepresentationOfSimilarity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRepresentation getActiveRepresentationOfDifferences() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IContextObject> getContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[][] getSimilarityMatrix() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[][] getAsymmetricalSimilarityMatrix() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[][] getDifferenceMatrix() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getTypicalityVector() {
		// TODO Auto-generated method stub
		return null;
	}

}
