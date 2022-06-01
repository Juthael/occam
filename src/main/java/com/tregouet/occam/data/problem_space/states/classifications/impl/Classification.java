package com.tregouet.occam.data.problem_space.states.classifications.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedTree;

public class Classification implements IClassification {
	
	private InvertedTree<IConcept, IIsA> graph;
	private Map<Integer, List<Integer>> conceptID2ExtentIDs;
	
	public Classification(InvertedTree<IConcept, IIsA> graph, Map<Integer, List<Integer>> conceptID2ExtentIDs) {
		this.graph = graph;
		this.conceptID2ExtentIDs = conceptID2ExtentIDs;
	}

	@Override
	public InvertedTree<IConcept, IIsA> asGraph() {
		return graph;
	}

	@Override
	public List<Integer> getExtentIDs(int conceptID) {
		return conceptID2ExtentIDs.get(conceptID);
	}

	@Override
	public Map<Integer, List<Integer>> mapConceptID2ExtentIDs() {
		return conceptID2ExtentIDs;
	}

	@Override
	public Set<IConcept> getMostSpecificConcepts() {
		return graph.getLeaves();
	}

	@Override
	public IConcept getConceptWithSpecifiedID(int iD) {
		for (IConcept concept : graph.vertexSet()) {
			if (concept.iD() == iD)
				return concept;
		}
		return null;
	}

}
