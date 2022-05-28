package com.tregouet.occam.alg.builders.pb_space.representations.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.data.problem_space.states.concepts.IComplementaryConcept;
import com.tregouet.occam.data.problem_space.states.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.utils.Functions;

public interface MapConceptIDs2ExtentIDs {
	
	public static Map<Integer, List<Integer>> in(InvertedTree<IConcept, IIsA> treeOfConcepts) {
		Map<Integer, List<Integer>> conceptID2ExtentIDs = new HashMap<>();
		for (IConcept concept : treeOfConcepts) {
			Set<Integer> extentIDs = new HashSet<>();
			Set<Integer> alreadyClassified = new HashSet<>();
			Set<IConcept> upperSet = Functions.upperSet(treeOfConcepts, concept);
			for (IConcept upperBound : upperSet) {
				if (upperBound.isComplementary()) {
					IConcept complemented = ((IComplementaryConcept) upperBound).getComplemented();
					alreadyClassified.addAll(complemented.getExtentIDs());
				}
			}
			for (Integer iD : concept.getExtentIDs()) {
				if (!alreadyClassified.contains(iD))
					extentIDs.add(iD);
			}
			List<Integer> sortedExtentIDs = new ArrayList<>(extentIDs);
			sortedExtentIDs.sort((x, y) -> Integer.compare(x, y));
			conceptID2ExtentIDs.put(concept.iD(), sortedExtentIDs);
		}
		return conceptID2ExtentIDs;
	}

}
