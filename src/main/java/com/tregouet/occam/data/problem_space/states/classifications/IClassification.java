package com.tregouet.occam.data.problem_space.states.classifications;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedTree;

public interface IClassification {
	
	InvertedTree<IConcept, IIsA> asGraph();
	
	List<Integer> getExtentIDs(int conceptID);
	
	Set<Integer> getParticularIDs();
	
	Map<Integer, List<Integer>> mapConceptID2ExtentIDs();
	
	Set<IConcept> getMostSpecificConcepts();
	
	IConcept getConceptWithSpecifiedID(int iD);
	
	IConcept getGenus(IConcept concept);
	
	IConcept getGenus(int iD);
	
	int getGenusID(int iD);

}
