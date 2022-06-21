package com.tregouet.occam.alg.scorers.similarity.relative.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.alg.scorers.similarity.relative.RelativeAsymmetricalSimilarityScorer;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

public class RelativeAsymmetricalDynamicFraming extends RelativeAbstractSimilarityScorer
		implements RelativeAsymmetricalSimilarityScorer {

	public RelativeAsymmetricalDynamicFraming() {
	}

	@Override
	public double score(Integer conceptID1, Integer conceptID2) {
		Double genusDefinitionCost;
		Double targetDefinitionCost;
		Integer genusID = Functions.commonAncestor(classificationTree, conceptID1, conceptID2);
		Integer targetAsGenusSpeciesID = findTargetAsGenusSpeciesID(genusID, conceptID1);
		genusDefinitionCost = getDefinitionCostOf(genusID);
		targetDefinitionCost = getDefinitionCostOf(targetAsGenusSpeciesID);
		return genusDefinitionCost / targetDefinitionCost;
	}

	@Override
	public RelativeAsymmetricalSimilarityScorer setAsContext(Tree<Integer, ADifferentiae> classificationTree) {
		this.classificationTree = classificationTree;
		return this;
	}

	private Integer findTargetAsGenusSpeciesID(Integer genusID, Integer targetID) {
		if (genusID.equals(targetID))
			return genusID;
		Integer targetAsGenusSpeciesID = null;
		List<Integer> genusSuccessors = new ArrayList<>(successorListOf(classificationTree, genusID));
		Iterator<Integer> genusSuccessorIte = genusSuccessors.iterator();
		while (targetAsGenusSpeciesID == null && genusSuccessorIte.hasNext()) {
			Integer nextSuccessor = genusSuccessorIte.next();
			if (nextSuccessor.equals(targetID) || classificationTree.getDescendants(nextSuccessor).contains(targetID)) {
				targetAsGenusSpeciesID = nextSuccessor;
			}
		}
		return targetAsGenusSpeciesID;
	}

	private List<Integer> successorListOf(Tree<Integer, ADifferentiae> classificationTree, Integer genusID) {
		List<Integer> successors = new ArrayList<>();
		for (ADifferentiae diff : classificationTree.outgoingEdgesOf(genusID))
			successors.add(diff.getTarget());
		return successors;
	}

}
