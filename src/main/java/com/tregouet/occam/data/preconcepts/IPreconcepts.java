package com.tregouet.occam.data.preconcepts;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.generators.preconcepts.IPreconceptTreeSupplier;
import com.tregouet.tree_finder.data.UpperSemilattice;

public interface IPreconcepts {
	
	@Override
	public int hashCode();
	
	boolean areA(List<IPreconcept> cats, IPreconcept cat);
	
	@Override
	boolean equals(Object o);
	
	IPreconcept getAbsurdity();
	
	IPreconceptTreeSupplier getConceptTreeSupplier() throws IOException;
	
	DirectedAcyclicGraph<IPreconcept, IIsA> getLatticeOfConcepts();
	
	/**
	 * If param contains every object in the context, then return truism
	 * @param extent
	 * @return
	 */
	IPreconcept getConceptWithExtent(Set<IContextObject> extent);
	
	List<IContextObject> getContextObjects();
	
	IPreconcept getLeastCommonSuperordinate(Set<IPreconcept> preconcepts);
	
	IPreconcept getOntologicalCommitment();
	
	UpperSemilattice<IPreconcept, IIsA> getOntologicalUpperSemilattice();
	
	//it is guaranteed that the order is the same as getContextObjects();
	List<IPreconcept> getObjectConcepts();
	
	List<IPreconcept> getTopologicalSorting();
	
	DirectedAcyclicGraph<IPreconcept, IIsA> getTransitiveReduction();
	
	IPreconcept getTruism();
	
	/**
	 * Not a reflexive relation
	 * @param cat1
	 * @param cat2
	 * @return
	 */
	boolean isA(IPreconcept cat1, IPreconcept cat2);

	boolean isADirectSubordinateOf(IPreconcept cat1, IPreconcept cat2);

}
