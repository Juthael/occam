package com.tregouet.occam.data.representations.concepts;

import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.tree_finder.data.InvertedUpperSemilattice;

public interface IConceptLattice {
	
	@Override
	public int hashCode();
	
	boolean areA(List<IConcept> cats, IConcept cat);
	
	@Override
	boolean equals(Object o);
	
	IConcept getAbsurdity();
	
	DirectedAcyclicGraph<IConcept, IIsA> getLatticeOfConcepts();
	
	/**
	 * If param contains every object in the context, then return truism
	 * @param extent
	 * @return
	 */
	IConcept getConceptWithExtent(Set<IContextObject> extent);
	
	List<IContextObject> getContextObjects();
	
	IConcept getLeastCommonSuperordinate(Set<IConcept> concepts);
	
	IConcept getOntologicalCommitment();
	
	InvertedUpperSemilattice<IConcept, IIsA> getOntologicalUpperSemilattice();
	
	//it is guaranteed that the order is the same as getContextObjects();
	List<IConcept> getObjectConcepts();
	
	List<IConcept> getTopologicalSorting();
	
	DirectedAcyclicGraph<IConcept, IIsA> getTransitiveReduction();
	
	IConcept getTruism();
	
	/**
	 * Not a reflexive relation
	 * @param cat1
	 * @param cat2
	 * @return
	 */
	boolean isA(IConcept cat1, IConcept cat2);

	boolean isADirectSubordinateOf(IConcept cat1, IConcept cat2);

}