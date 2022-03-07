package com.tregouet.occam.alg.scoring.scores.similarity.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.scoring.scores.similarity.ISimilarityScorer;
import com.tregouet.occam.data.automata.machines.IAutomaton;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

public abstract class AbstractSimCalculator implements ISimilarityScorer {

	protected IAutomaton automaton;
	protected List<IPreconcept> singletons;
	protected int[] singletonIDs;
	
	public AbstractSimCalculator() {
	}
	
	public AbstractSimCalculator(IAutomaton automaton) {
		input(automaton);
	}	

	@Override
	public double[][] getAsymmetricalSimilarityMatrix() {
		int nbOfObjects = singletonIDs.length;
		double[][] asymmetricalSimMatrix = new double[nbOfObjects][nbOfObjects];
		for (int i = 0 ; i < nbOfObjects ; i++) {
			for (int j = 0 ; j < nbOfObjects ; j++) {
				if (i == j)
					asymmetricalSimMatrix[i][j] = 1;
				else asymmetricalSimMatrix[i][j] = howSimilarTo(singletonIDs[i], singletonIDs[j]);
			}
		}
		return asymmetricalSimMatrix;
	}

	@Override
	public double getCoherenceScore() {
		return getCoherenceScore(singletonIDs);
	}
	
	@Override
	public double getCoherenceScore(int[] cncptIDs) {
		if (cncptIDs.length == 1)
			return 1.0;
		double similaritySum = 0.0;
		double n = cncptIDs.length;
		for (int i = 0 ; i < cncptIDs.length - 1 ; i++) {
			for (int j = i + 1 ; j < cncptIDs.length ; j++) {
				similaritySum += howSimilar(cncptIDs[i], cncptIDs[j]);
			}
		}
		return similaritySum / ((n*(n-1))/2);
	}

	@Override
	public Map<Integer, Double> getConceptualCoherenceMap() {
		Map<Integer, Double> cncptIDToCoherenceScore = new HashMap<>();
		Tree<IPreconcept, IIsA> treeOfDenotationSets = automaton.getTreeOfDenotationSets();
		Iterator<IPreconcept> iterator = treeOfDenotationSets.getTopologicalOrder().iterator();
		Set<IPreconcept> singletonSet = new HashSet<>(treeOfDenotationSets.getLeaves());
		while (iterator.hasNext()) {
			IPreconcept nextCat = iterator.next();
			Set<IPreconcept> lowerBoundSingletons = 
					Sets.intersection(Functions.lowerSet(treeOfDenotationSets, nextCat), singletonSet);
			int[] extentIDs = new int[lowerBoundSingletons.size()];
			int singletonIdx = 0;
			Iterator<IPreconcept> lowerBoundSingletonIte = lowerBoundSingletons.iterator();
			while (lowerBoundSingletonIte.hasNext()) {
				extentIDs[singletonIdx++] = lowerBoundSingletonIte.next().getID();
			}
			cncptIDToCoherenceScore.put(nextCat.getID(), getCoherenceScore(extentIDs));
		}
		return cncptIDToCoherenceScore;
	}

	@Override
	public List<IPreconcept> getListOfSingletons() {
		return new ArrayList<>(singletons);
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
					double ijSimilarity = howSimilar(singletonIDs[i], singletonIDs[j]);
					similarityMatrix[i][j] = ijSimilarity;
					similarityMatrix[j][i] = ijSimilarity;
				}
			}
		}
		return similarityMatrix;
	}
	
	@Override
	public double[] getTypicalityArray() {
		int nbOfObjects = singletonIDs.length;
		double[] typicalityArray = new double[nbOfObjects];
		for (int i = 0 ; i < nbOfObjects ; i++) {
			typicalityArray[i] = howProtoypical(singletonIDs[i]);
		}
		return typicalityArray;
	}	
	
	@Override
	public double howPrototypicalAmong(int denotationID, int[] otherDenotationsIDs) {
		double similarityToParameterSum = 0.0;
		int nbOfComparisons = 0;
		for (int otherDenotationID : otherDenotationsIDs) {
			if (denotationID != otherDenotationID) {
				similarityToParameterSum += howSimilarTo(otherDenotationID, denotationID);
				nbOfComparisons++;
			}
		}
		return similarityToParameterSum / nbOfComparisons;
	}
	
	@Override
	public double howProtoypical(int denotationID) {
		int n = 0;
		double similaritySum = 0.0;
		for (int singletonID : singletonIDs) {
			if (denotationID != singletonID) {
				similaritySum += howSimilarTo(singletonID, denotationID);
				n++;
			}
		}
		return similaritySum / n;
	}		
	
	@Override
	abstract public double howSimilar(int denotationID1, int denotationID2);	
	
	@Override
	abstract public double howSimilarTo(int denotationID1, int denotationID2);	
	
	@Override
	public ISimilarityScorer input(IAutomaton automaton) {
		this.automaton = automaton;
		singletons = new ArrayList<>(automaton.getTreeOfDenotationSets().getLeaves());
		Collections.sort(singletons, (c1, c2) -> c1.getID() - c2.getID());
		singletonIDs = new int[singletons.size()];
		int singletonIdx = 0;
		for (IPreconcept singleton : singletons) {
			singletonIDs[singletonIdx++] = singleton.getID(); 
		}
		return this;
	}	
	
	@Override
	public void setScore() {
		automaton.setScore(getCoherenceScore(singletonIDs));
	}

}
