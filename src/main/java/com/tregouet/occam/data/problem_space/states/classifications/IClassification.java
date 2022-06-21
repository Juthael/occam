package com.tregouet.occam.data.problem_space.states.classifications;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedTree;

public interface IClassification {

	InvertedTree<IConcept, IIsA> asGraph();

	@Override
	boolean equals(Object o);

	IConcept getConceptWithSpecifiedID(int iD);

	List<Integer> getExtentIDs(int conceptID);

	IConcept getGenus(IConcept concept);

	IConcept getGenus(int iD);

	int getGenusID(int iD);

	Set<IConcept> getMostSpecificConcepts();

	Set<Integer> getParticularIDs();

	@Override
	int hashCode();

	boolean isFullyDeveloped();

	Map<Integer, List<Integer>> mapConceptID2ExtentIDs();

	Map<Integer, Integer> mapSpeciesID2GenusID();

	IClassification normalized();

	@Override
	String toString();

}
