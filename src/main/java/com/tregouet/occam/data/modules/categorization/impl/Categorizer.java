package com.tregouet.occam.data.modules.categorization.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.categorizer.ProblemSpaceExplorer;
import com.tregouet.occam.data.modules.categorization.ICategorizer;
import com.tregouet.occam.data.modules.categorization.transitions.AProblemStateTransition;
import com.tregouet.occam.data.modules.similarity.metrics.ISimilarityMetrics;
import com.tregouet.occam.data.representations.IRepresentation;
import com.tregouet.occam.data.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.data.representations.classifications.concepts.IIsA;

public class Categorizer implements ICategorizer {

	private final List<IContextObject> context;
	private final ProblemSpaceExplorer problemSpaceExplorer;
	private ISimilarityMetrics similarityMetrics = null;
	private IRepresentation activeState = null;

	public Categorizer(Set<IContextObject> context) {
		this.context = new ArrayList<>(context);
		this.context.sort((x, y) -> Integer.compare(x.iD(), y.iD()));
		problemSpaceExplorer = ICategorizer.problemSpaceExplorer().initialize(this.context);
		similarityMetrics = problemSpaceExplorer.getSimilarityMetrics(null);
	}

	@Override
	public Boolean develop() {
		try {
			problemSpaceExplorer.develop();
		}
		catch (Exception e) {
			return null;
		}
		similarityMetrics = problemSpaceExplorer.getSimilarityMetrics(similarityMetrics.getDifferenceMatrix());
		return true;
	}

	@Override
	public Boolean develop(int representationID) {
		Boolean developed = problemSpaceExplorer.develop(representationID);
		if (developed != null && developed)
			similarityMetrics = problemSpaceExplorer.getSimilarityMetrics(similarityMetrics.getDifferenceMatrix());
		return developed;
	}

	@Override
	public Boolean develop(List<Integer> representationIDs) {
		Boolean developed = problemSpaceExplorer.develop(new HashSet<>(representationIDs));
		if (developed != null && developed)
			similarityMetrics = problemSpaceExplorer.getSimilarityMetrics(similarityMetrics.getDifferenceMatrix());
		return developed;
	}

	@Override
	public Boolean display(int representationID) {
		if (activeState != null && activeState.iD() == representationID)
			return false;
		boolean representationFound = false;
		Iterator<IRepresentation> repIte = problemSpaceExplorer.getProblemSpaceGraph().vertexSet().iterator();
		while (!representationFound && repIte.hasNext()) {
			IRepresentation nextRep = repIte.next();
			if (nextRep.iD() == representationID) {
				activeState = nextRep;
				representationFound = true;
			}
		}
		if (!representationFound)
			return null;
		return true;
	}

	@Override
	public IRepresentation getActiveRepresentation() {
		return activeState;
	}

	@Override
	public double[][] getAsymmetricalSimilarityMatrix() {
		return similarityMetrics.getAsymmetricalSimilarityMatrix();
	}

	@Override
	public List<IContextObject> getContext() {
		return context;
	}

	@Override
	public double[][] getDifferenceMatrix() {
		return similarityMetrics.getDifferenceMatrix();
	}

	@Override
	public DirectedAcyclicGraph<IConcept, IIsA> getLatticeOfConcepts() {
		return problemSpaceExplorer.getLatticeOfConcepts();
	}

	@Override
	public DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> getProblemSpaceGraph() {
		return problemSpaceExplorer.getProblemSpaceGraph();
	}

	@Override
	public String[][] getReferenceMatrix() {
		return similarityMetrics.getReferenceMatrix();
	}

	@Override
	public double[][] getSimilarityMatrix() {
		return similarityMetrics.getSimilarityMatrix();
	}

	@Override
	public double[] getTypicalityVector() {
		return similarityMetrics.getTypicalityVector();
	}

	@Override
	public Boolean restrictTo(Set<Integer> representationIDs) {
		Boolean restricted = problemSpaceExplorer.restrictTo(representationIDs);
		if (restricted != null && restricted)
			similarityMetrics = problemSpaceExplorer.getSimilarityMetrics(similarityMetrics.getDifferenceMatrix());
		return restricted;
	}

}
