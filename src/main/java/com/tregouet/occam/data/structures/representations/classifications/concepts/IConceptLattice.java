package com.tregouet.occam.data.structures.representations.classifications.concepts;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.tree_finder.data.InvertedUpperSemilattice;

/*
 * The lattice is supposed to be transitively reduced.
 */
public interface IConceptLattice {

	@Override
	public int hashCode();

	boolean areA(List<IConcept> cats, IConcept cat);

	@Override
	boolean equals(Object o);

	IConcept getAbsurdity();

	/**
	 * If param contains every object in the context, then return truism
	 *
	 * @param extent
	 * @return
	 */
	IConcept getConceptWithExtentIDs(Set<Integer> extentIDs);

	List<IContextObject> getContextObjects();

	DirectedAcyclicGraph<IConcept, IIsA> getLatticeOfConcepts();

	IConcept getLeastCommonSuperordinate(Set<IConcept> concepts);

	IConcept getOntologicalCommitment();

	InvertedUpperSemilattice<IConcept, IIsA> getOntologicalUpperSemilattice();

	Map<Integer, IConcept> getParticularID2Particular();

	// it is guaranteed that the order is the same as getContextObjects();
	List<IConcept> getParticulars();

	List<IConcept> getTopologicalSorting();

	DirectedAcyclicGraph<IConcept, IIsA> getTransitiveReduction();

	IConcept getTruism();

	/**
	 * Not a reflexive relation
	 *
	 * @param cat1
	 * @param cat2
	 * @return
	 */
	boolean isA(IConcept cat1, IConcept cat2);

	boolean isADirectSubordinateOf(IConcept cat1, IConcept cat2);
	
	IConcept getConceptWithSpecifiedID(int iD);

}
