package com.tregouet.occam.alg.builders.classifications.impl.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.data.structures.representations.classifications.concepts.IComplementaryConcept;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.utils.Functions;

public interface MapConceptIDs2ExtentIDs {

	public static Map<Integer, List<Integer>> in(InvertedTree<IConcept, IIsA> treeOfConcepts) {
		List<Integer> conceptIDs = new ArrayList<>();
		List<Set<Integer>> extents = new ArrayList<>();
		for (IConcept concept : treeOfConcepts.vertexSet()) {
			conceptIDs.add(concept.iD());
			extents.add(new HashSet<>(concept.getMaxExtentIDs()));
		}
		for (IConcept concept : treeOfConcepts.vertexSet()) {
			if (concept.isComplementary()) {
				IComplementaryConcept complementary = (IComplementaryConcept) concept;
				IConcept complemented = complementary.getComplemented();
				Set<Integer> complementedExtent = complemented.getMaxExtentIDs();
				Set<IConcept> complementaryLowerSet = Functions.lowerSet(treeOfConcepts, complementary);
				for (IConcept lowerBound : complementaryLowerSet) {
					extents.get(conceptIDs.indexOf(lowerBound.iD())).removeAll(complementedExtent);
				}
			}
		}
		Map<Integer, List<Integer>> conceptID2ExtentIDs = new HashMap<>();
		for (int i = 0 ; i < conceptIDs.size() ; i++) {
			List<Integer> iExtent = new ArrayList<>(extents.get(i));
			iExtent.sort((x, y) -> Integer.compare(x, y));
			conceptID2ExtentIDs.put(conceptIDs.get(i), iExtent);
		}
		return conceptID2ExtentIDs;
	}

}
