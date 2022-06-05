package com.tregouet.occam.data.problem_space.states.classifications.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedTree;

public class Classification implements IClassification {
	
	private InvertedTree<IConcept, IIsA> graph;
	private Map<Integer, IConcept> iD2Concept = new HashMap<>();
	private Map<Integer, List<Integer>> conceptID2ExtentIDs;
	private Map<Integer, Integer> speciesID2GenusID;
	private Set<Integer> extentIDs;
	
	public Classification(InvertedTree<IConcept, IIsA> graph, Map<Integer, List<Integer>> conceptID2ExtentIDs, 
			Map<Integer, Integer> speciesID2GenusID, Set<Integer> extentIDs) {
		this.graph = graph;
		for (IConcept concept : graph.vertexSet())
			iD2Concept.put(concept.iD(), concept);
		this.conceptID2ExtentIDs = conceptID2ExtentIDs;
		this.speciesID2GenusID = speciesID2GenusID;
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
		return iD2Concept.get(iD);
	}

	@Override
	public IConcept getGenus(IConcept concept) {
		return iD2Concept.get(speciesID2GenusID.get(concept.iD()));
	}

	@Override
	public IConcept getGenus(int iD) {
		return iD2Concept.get(speciesID2GenusID.get(iD));
	}

	@Override
	public int getGenusID(int iD) {
		return speciesID2GenusID.get(iD);
	}

	@Override
	public Set<Integer> getParticularIDs() {
		return extentIDs;
	}

}
