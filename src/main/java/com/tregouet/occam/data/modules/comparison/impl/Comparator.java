package com.tregouet.occam.data.modules.comparison.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jgrapht.alg.util.UnorderedPair;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.comparator.ComparatorSetter;
import com.tregouet.occam.data.modules.comparison.IComparator;
import com.tregouet.occam.data.modules.comparison.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IIsA;

public class Comparator implements IComparator {

	private List<IContextObject> context = null;
	private IConceptLattice conceptLattice = null;
	private Map<UnorderedPair<Integer, Integer>, IRepresentation> dichotomies = null;
	private Map<UnorderedPair<Integer, Integer>, IRepresentation> differences = null;
	private ISimilarityMetrics similarityMetrics = null;
	private IRepresentation activeRepOfSimilarity = null;
	private IRepresentation activeRepOfDifferences = null;
	private UnorderedPair<Integer, Integer> comparedPair = null;

	public Comparator() {
	}

	@Override
	public Boolean display(int particularID1, int particularID2) {
		UnorderedPair<Integer, Integer> comparedPair = UnorderedPair.of(particularID1, particularID2);
		IRepresentation dichotomy = dichotomies.get(comparedPair);
		IRepresentation diff = differences.get(comparedPair);
		if (dichotomy == null || diff == null)
			return null;
		if (dichotomy.equals(activeRepOfSimilarity))
			return false;
		activeRepOfSimilarity = dichotomy;
		activeRepOfDifferences = diff;
		this.comparedPair = comparedPair;
		return true;
	}

	@Override
	public IRepresentation getActiveRepresentationOfDifferences() {
		return activeRepOfDifferences;
	}

	@Override
	public IRepresentation getActiveRepresentationOfSimilarity() {
		return activeRepOfSimilarity;
	}

	@Override
	public Double[][] getAsymmetricalSimilarityMatrix() {
		return similarityMetrics.getAsymmetricalSimilarityMatrix();
	}

	@Override
	public String[][] getAsymmetricalSimilarityStringMatrix() {
		return IComparator.matrixFormatter().apply(getAsymmetricalSimilarityMatrix());
	}

	@Override
	public List<IContextObject> getContext() {
		return context;
	}

	@Override
	public Double[][] getDifferenceMatrix() {
		return similarityMetrics.getDifferenceMatrix();
	}

	@Override
	public String[][] getDifferenceStringMatrix() {
		return IComparator.matrixFormatter().apply(getDifferenceMatrix());
	}

	@Override
	public DirectedAcyclicGraph<IConcept, IIsA> getLatticeOfConcepts() {
		return conceptLattice.getLatticeOfConcepts();
	}

	@Override
	public Double[][] getSimilarityMatrix() {
		return similarityMetrics.getSimilarityMatrix();
	}

	@Override
	public String[][] getSimilarityStringMatrix() {
		return IComparator.matrixFormatter().apply(getSimilarityMatrix());
	}

	@Override
	public String[] getTypicalityStringVector() {
		return IComparator.matrixFormatter().apply(getTypicalityVector());
	}

	@Override
	public double[] getTypicalityVector() {
		return similarityMetrics.getTypicalityVector();
	}

	@Override
	public IComparator process(Collection<IContextObject> contextColl) {
		List<IContextObject> context = new ArrayList<>(contextColl);
		context.sort((x, y) -> Integer.compare(x.iD(), y.iD()));
		ComparatorSetter setter = IComparator.comparatorSetter().accept(context);
		this.context = context;
		this.conceptLattice = setter.getConceptLattice();
		this.dichotomies = setter.getDichotomies();
		this.differences = setter.getDifferences();
		this.similarityMetrics = setter.getSimilarityMetrics();
		return this;
	}

	@Override
	public UnorderedPair<Integer, Integer> getComparedPair() {
		return comparedPair;
	}

}
