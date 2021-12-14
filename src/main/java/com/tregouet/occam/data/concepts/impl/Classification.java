package com.tregouet.occam.data.concepts.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tregouet.occam.alg.score_calc.CalculatorFactory;
import com.tregouet.occam.alg.score_calc.concept_derivation_cost.IConceptDerivationCostCalculator;
import com.tregouet.occam.alg.score_calc.similarity_calc.ISimilarityCalculator;
import com.tregouet.occam.data.concepts.IClassification;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIsA;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

public class Classification implements IClassification {

	private final Tree<IConcept, IIsA> classificationTree;
	private final List<IConcept> singletons;
	private final IConceptDerivationCostCalculator costCalc;
	private final ISimilarityCalculator similarityCalc;
	private double[][] similarityMatrix = null;
	private double[][] asymmetricalSimilarityMatrix = null;
	private final double coherenceScore;
	
	public Classification(Tree<IConcept, IIsA> classificationTree, List<IConcept> singletons) {
		this.classificationTree = classificationTree;
		this.singletons = singletons;
		costCalc = CalculatorFactory.INSTANCE.getConceptDerivationCostCalculator().input(classificationTree);
		similarityCalc = CalculatorFactory.INSTANCE.getSimilarityCalculator().input(this);
		coherenceScore = similarityCalc.getCoherenceScore();
	}

	@Override
	public int compareTo(IClassification o) {
		if (o == this)
			return 0;
		double otherCoherenceScore = o.getCoherenceScore();
		if (coherenceScore > otherCoherenceScore)
			return 1;
		if (coherenceScore < otherCoherenceScore)
			return -1;
		int thisNbOfEdges = classificationTree.edgeSet().size();
		int otherNbOfEdges = o.getClassificationTree().edgeSet().size();
		if (thisNbOfEdges < otherNbOfEdges)
			return 1;
		if (thisNbOfEdges > otherNbOfEdges)
			return -1;
		return (System.identityHashCode(this) - System.identityHashCode(o));
	}

	@Override
	public double[][] getAsymmetricalSimilarityMatrix() {
		if (asymmetricalSimilarityMatrix == null) {
			int nbOfObjects = singletons.size();
			asymmetricalSimilarityMatrix = new double[nbOfObjects][nbOfObjects];
			for (int i = 0 ; i < nbOfObjects ; i++) {
				int iObjCatID = singletons.get(i).getID();
				asymmetricalSimilarityMatrix[i][i] = 1.0;
				for (int j = i + 1 ; j < nbOfObjects ; j++) {
					int jObjCatID = singletons.get(j).getID();
					asymmetricalSimilarityMatrix[i][j] = similarityCalc.howSimilarTo(iObjCatID, jObjCatID);
					asymmetricalSimilarityMatrix[j][i] = similarityCalc.howSimilarTo(jObjCatID, iObjCatID);
				}
			}
		}
		return asymmetricalSimilarityMatrix;
	}

	@Override
	public Tree<IConcept, IIsA> getClassificationTree() {
		return classificationTree;
	}

	@Override
	public double getCoherenceScore() {
		return coherenceScore;
	}

	@Override
	public Map<Integer, Double> getConceptualCoherenceMap() {
		Map<Integer, Double> catIDToCoherenceScore = new HashMap<>();
		Iterator<IConcept> iterator = classificationTree.getTopologicalOrder().iterator();
		Set<IConcept> singletonSet = new HashSet<>(singletons);
		while (iterator.hasNext()) {
			IConcept nextCat = iterator.next();
			Set<IConcept> lowerBoundSingletons = 
					Sets.intersection(Functions.lowerSet(classificationTree, nextCat), singletonSet);
			int[] extentIDs = new int[lowerBoundSingletons.size()];
			int singletonIdx = 0;
			Iterator<IConcept> lowerBoundSingletonIte = lowerBoundSingletons.iterator();
			while (lowerBoundSingletonIte.hasNext()) {
				extentIDs[singletonIdx++] = lowerBoundSingletonIte.next().getID();
			}
			catIDToCoherenceScore.put(nextCat.getID(), similarityCalc.getCoherenceScore(extentIDs));
		}
		return catIDToCoherenceScore;
	}

	@Override
	public double getCostOf(IIsA derivation) {
		return costCalc.costOf(derivation);
	}

	@Override
	public ISimilarityCalculator getSimilarityCalculator() {
		return similarityCalc;
	}

	@Override
	public double[][] getSimilarityMatrix() {
		if (similarityMatrix == null) {
			int nbOfObjects = singletons.size();
			similarityMatrix = new double[nbOfObjects][nbOfObjects];
			for (int i = 0 ; i < nbOfObjects ; i++) {
				int iSingletonID = singletons.get(i).getID();
				similarityMatrix[i][i] = 1.0;
				for (int j = i + 1 ; j < nbOfObjects ; j++) {
					int jSingletonID = singletons.get(j).getID();
					double similarityScoreIJ = similarityCalc.howSimilar(iSingletonID, jSingletonID);
					similarityMatrix[i][j] = similarityScoreIJ;
					similarityMatrix[j][i] = similarityScoreIJ;
				}
			}
		}
		return similarityMatrix;
	}

	@Override
	public double[] getTypicalityArray() {
		int nbOfObjects = singletons.size();
		double[] typicalityArray = new double[nbOfObjects];
		for (int i = 0 ; i < nbOfObjects ; i++) {
			typicalityArray[i] = similarityCalc.howProtoypical(singletons.get(i).getID());
		}
		return typicalityArray;
	}

}
