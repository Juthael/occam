package com.tregouet.occam.alg.scorers.similarity.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.alg.scorers.similarity.AsymmetricalSimilarityScorer;
import com.tregouet.occam.data.logical_structures.scores.impl.DoubleScore;
import com.tregouet.occam.data.problem_space.states.descriptions.metrics.subsets.IConceptPairIDs;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;
import com.tregouet.tree_finder.utils.Functions;

public class AsymmetricalDynamicFraming extends AbstractSimilarityScorer<IConceptPairIDs>
		implements AsymmetricalSimilarityScorer {

	public AsymmetricalDynamicFraming() {
	}

	@Override
	public DoubleScore apply(IConceptPairIDs iDPair) {
		Double genusDefinitionCost;
		Double targetDefinitionCost;
		Integer genusID = Functions.commonAncestor(classificationTree, iDPair.second(), iDPair.first());
		Integer targetAsGenusSpeciesID = findTargetAsGenusSpeciesID(genusID, iDPair.first());
		genusDefinitionCost = getDefinitionCostOf(genusID);
		targetDefinitionCost = getDefinitionCostOf(targetAsGenusSpeciesID);
		return new DoubleScore(genusDefinitionCost / targetDefinitionCost);
	}

	@Override
	public AsymmetricalSimilarityScorer setAsContext(Tree<Integer, ADifferentiae> classificationTree) {
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
