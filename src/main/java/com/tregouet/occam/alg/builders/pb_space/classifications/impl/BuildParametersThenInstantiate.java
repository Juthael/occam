package com.tregouet.occam.alg.builders.pb_space.classifications.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.alg.builders.pb_space.classifications.ClassificationBuilder;
import com.tregouet.occam.alg.builders.pb_space.utils.MapConceptIDs2ExtentIDs;
import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.ConceptType;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.states.classifications.impl.Classification;
import com.tregouet.tree_finder.data.InvertedTree;

public class BuildParametersThenInstantiate implements ClassificationBuilder {

	public static final BuildParametersThenInstantiate INSTANCE = new BuildParametersThenInstantiate();

	private BuildParametersThenInstantiate() {
	}

	@Override
	public IClassification apply(InvertedTree<IConcept, IIsA> conceptTree, Map<Integer, IConcept> particularID2Particular) {
		Map<Integer, List<Integer>> conceptID2ExtentID = MapConceptIDs2ExtentIDs.in(conceptTree);
		Map<Integer, Integer> speciesID2GenusID = mapSpeciesID2GenusID(conceptTree);
		boolean fullyDeveloped = isFullyDeveloped(conceptTree);
		return new Classification(conceptTree, conceptID2ExtentID, speciesID2GenusID, particularID2Particular,
				fullyDeveloped);
	}

	private static boolean isFullyDeveloped(InvertedTree<IConcept, IIsA> conceptTree) {
		for (IConcept concept : conceptTree.getLeaves()) {
			if (concept.type() != ConceptType.PARTICULAR)
				return false;
		}
		return true;
	}

	private static Map<Integer, Integer> mapSpeciesID2GenusID(InvertedTree<IConcept, IIsA> conceptTree) {
		Map<Integer, Integer> speciesID2GenusID = new HashMap<>();
		for (IIsA edge : conceptTree.edgeSet())
			speciesID2GenusID.put(conceptTree.getEdgeSource(edge).iD(), conceptTree.getEdgeTarget(edge).iD());
		return speciesID2GenusID;
	}

}
