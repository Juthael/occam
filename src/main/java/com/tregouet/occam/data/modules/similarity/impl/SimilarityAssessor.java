package com.tregouet.occam.data.modules.similarity.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.alg.util.UnorderedPair;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.similarity_assessor.SimAssessorSetter;
import com.tregouet.occam.data.modules.similarity.ISimilarityAssessor;
import com.tregouet.occam.data.modules.similarity.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IIsA;

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
	public Double[][] getSimilarityMatrix() {
		return similarityMetrics.getSimilarityMatrix();
	}

	@Override
	public Double[][] getAsymmetricalSimilarityMatrix() {
		return similarityMetrics.getAsymmetricalSimilarityMatrix();
	}

	@Override
	public Double[][] getDifferenceMatrix() {
		return similarityMetrics.getDifferenceMatrix();
	}

	@Override
	public double[] getTypicalityVector() {
		return similarityMetrics.getTypicalityVector();
	}

	@Override
	public ISimilarityAssessor process(Collection<IContextObject> contextColl) {
		List<IContextObject> context = new ArrayList<>(contextColl);
		context.sort((x, y) -> Integer.compare(x.iD(), y.iD()));
		SimAssessorSetter setter = ISimilarityAssessor.simAssessorSetter().accept(context);
		this.context = context;
		this.conceptLattice = setter.getConceptLattice();
		this.dichotomies = setter.getDichotomies();
		this.differences = setter.getDifferences();
		this.similarityMetrics = setter.getSimilarityMetrics();
		return this;
	}

	@Override
	public String[][] getSimilarityStringMatrix() {
		return ISimilarityAssessor.matrixFormatter().apply(getSimilarityMatrix());
	}

	@Override
	public String[][] getAsymmetricalSimilarityStringMatrix() {
		return ISimilarityAssessor.matrixFormatter().apply(getAsymmetricalSimilarityMatrix());
	}

	@Override
	public String[][] getDifferenceStringMatrix() {
		return ISimilarityAssessor.matrixFormatter().apply(getDifferenceMatrix());
	}

	@Override
	public String[] getTypicalityStringVector() {
		return ISimilarityAssessor.matrixFormatter().apply(getTypicalityVector());
	}

}
