package com.tregouet.occam.data.problem_space.states.classifications.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedTree;

public class NormalizedClassification extends Classification implements IClassification {

	public NormalizedClassification(InvertedTree<IConcept, IIsA> graph,
			Map<Integer, List<Integer>> conceptID2ExtentIDs, Map<Integer, Integer> speciesID2GenusID,
			Set<Integer> particularIDs) {
		super(graph, conceptID2ExtentIDs, speciesID2GenusID, particularIDs);
	}
	
	@Override
	public NormalizedClassification normalized() {
		return this;
	}	

}
