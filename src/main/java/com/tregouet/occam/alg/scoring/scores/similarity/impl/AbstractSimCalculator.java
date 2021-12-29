package com.tregouet.occam.alg.scoring.scores.similarity.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.scoring.scores.similarity.ISimilarityScorer;
import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

public abstract class AbstractSimCalculator implements ISimilarityScorer {

	protected ITransitionFunction transitionFunction;
	protected List<IConcept> singletons;
	protected int[] singletonIDs;
	
	public AbstractSimCalculator() {
	}
	
	public AbstractSimCalculator(ITransitionFunction transitionFunction) {
		input(transitionFunction);
	}	

	@Override
	public double getCoherenceScore() {
		return getCoherenceScore(singletonIDs);
	}

	@Override
	public double getCoherenceScore(int[] conceptIDs) {
		if (conceptIDs.length == 1)
			return 1.0;
		double similaritySum = 0.0;
		double n = conceptIDs.length;
		for (int i = 0 ; i < conceptIDs.length - 1 ; i++) {
			for (int j = i + 1 ; j < conceptIDs.length ; j++) {
				similaritySum += howSimilar(conceptIDs[i], conceptIDs[j]);
			}
		}
		return similaritySum / ((n*(n-1))/2);
	}
	
	@Override
	public double howPrototypicalAmong(int conceptID, int[] otherConceptsIDs) {
		double similarityToParameterSum = 0.0;
		int nbOfComparisons = 0;
		for (int otherConceptID : otherConceptsIDs) {
			if (conceptID != otherConceptID) {
				similarityToParameterSum += howSimilarTo(otherConceptID, conceptID);
				nbOfComparisons++;
			}
		}
		return similarityToParameterSum / nbOfComparisons;
	}

	@Override
	abstract public double howSimilar(int conceptID1, int conceptID2);

	@Override
	abstract public double howSimilarTo(int conceptID1, int conceptID2);
	
	public double[][] getAsymmetricalSimilarityMatrix() {
		int nbOfObjects = singletonIDs.length;
		double[][] asymmetricalSimMatrix = new double[nbOfObjects][nbOfObjects];
		for (int i = 0 ; i < nbOfObjects ; i++) {
			for (int j = 0 ; j < nbOfObjects ; j++) {
				if (i == j)
					asymmetricalSimMatrix[i][j] = 1;
				else asymmetricalSimMatrix[i][j] = howSimilarTo(i, j);
			}
		}
		return asymmetricalSimMatrix;
	}
	
	@Override
	public double[][] getSimilarityMatrix() {
		int nbOfObjects = singletonIDs.length;
		double[][] similarityMatrix = new double[nbOfObjects][nbOfObjects];
		for (int i = 0 ; i < nbOfObjects ; i++) {
			for (int j = i ; j < nbOfObjects ; j++) {
				if (i == j)
					similarityMatrix[i][j] = 1;
				else {
					double ijSimilarity = howSimilar(i, j);
					similarityMatrix[i][j] = ijSimilarity;
					similarityMatrix[j][i] = ijSimilarity;
				}
			}
		}
		return similarityMatrix;
	}	
	
	public double[] getTypicalityArray() {
		int nbOfObjects = singletonIDs.length;
		double[] typicalityArray = new double[nbOfObjects];
		for (int i = 0 ; i < nbOfObjects ; i++) {
			typicalityArray[i] = howProtoypical(singletonIDs[i]);
		}
		return typicalityArray;
	}
	
	@Override
	public ISimilarityScorer input(ITransitionFunction transitionFunction) {
		this.transitionFunction = transitionFunction;
		singletons = new ArrayList<>(transitionFunction.getTreeOfConcepts().getLeaves());
		singletonIDs = new int[singletons.size()];
		int singletonIdx = 0;
		for (IConcept singleton : singletons) {
			singletonIDs[singletonIdx++] = singleton.getID(); 
		}
		return this;
	}		
	
	@Override
	public Map<Integer, Double> getConceptualCoherenceMap() {
		Map<Integer, Double> conceptIDToCoherenceScore = new HashMap<>();
		Tree<IConcept, IIsA> treeOfConcepts = transitionFunction.getTreeOfConcepts();
		Iterator<IConcept> iterator = treeOfConcepts.getTopologicalOrder().iterator();
		Set<IConcept> singletonSet = new HashSet<>(treeOfConcepts.getLeaves());
		while (iterator.hasNext()) {
			IConcept nextCat = iterator.next();
			Set<IConcept> lowerBoundSingletons = 
					Sets.intersection(Functions.lowerSet(treeOfConcepts, nextCat), singletonSet);
			int[] extentIDs = new int[lowerBoundSingletons.size()];
			int singletonIdx = 0;
			Iterator<IConcept> lowerBoundSingletonIte = lowerBoundSingletons.iterator();
			while (lowerBoundSingletonIte.hasNext()) {
				extentIDs[singletonIdx++] = lowerBoundSingletonIte.next().getID();
			}
			conceptIDToCoherenceScore.put(nextCat.getID(), getCoherenceScore(extentIDs));
		}
		return conceptIDToCoherenceScore;
	}	
	
	@Override
	public List<IConcept> getListOfSingletons() {
		return new ArrayList<>(singletons);
	}	
	
	@Override
	public double howProtoypical(int conceptID) {
		int n = 0;
		double similaritySum = 0.0;
		for (int singletonID : singletonIDs) {
			if (conceptID != singletonID) {
				similaritySum += howSimilarTo(singletonID, conceptID);
				n++;
			}
		}
		return similaritySum / (double) n;
	}	
	
	@Override
	public void setScore() {
		transitionFunction.setScore(getCoherenceScore(singletonIDs));
	}

}
