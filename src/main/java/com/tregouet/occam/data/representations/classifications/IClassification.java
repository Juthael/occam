package com.tregouet.occam.data.representations.classifications;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tregouet.occam.data.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.representations.classifications.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedTree;

public interface IClassification {

	InvertedTree<IConcept, IIsA> asGraph();

	@Override
	boolean equals(Object o);

	boolean expansionIsRestricted();

	IConcept getConceptWithSpecifiedID(int iD);

	List<IConcept> getExtent(int conceptID);

	List<Integer> getExtentIDs(int conceptID);

	IConcept getGenus(IConcept concept);

	IConcept getGenus(int iD);

	int getGenusID(int iD);

	Set<IConcept> getMostSpecificConcepts();

	Map<Integer, IConcept> getParticularID2Particular();

	Set<Integer> getParticularIDs();

	@Override
	int hashCode();

	boolean isExpandable();

	boolean isFullyDeveloped();

	Map<Integer, List<Integer>> mapConceptID2ExtentIDs();

	Map<Integer, Integer> mapSpeciesID2GenusID();

	IClassification normalized();

	void restrictFurtherExpansion();

	@Override
	String toString();

}
