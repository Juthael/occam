package com.tregouet.occam.data.concepts;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.tree_finder.data.UpperSemilattice;

public interface IConcepts {
	
	@Override
	public int hashCode();
	
	boolean areA(List<IConcept> cats, IConcept cat);
	
	@Override
	boolean equals(Object o);
	
	IConcept getAbsurdity();
	
	DirectedAcyclicGraph<IConcept, IsA> getCategoryLattice();
	
	IClassificationTreeSupplier getCatTreeSupplier() throws IOException;
	
	IClassTreeWithConstrainedExtentStructureSupplier getCatTreeSupplier(IExtentStructureConstraint constraint);
	
	/**
	 * If param contains every object in the context, then return truism
	 * @param extent
	 * @return
	 */
	IConcept getConceptWithExtent(Set<IContextObject> extent);
	
	List<IContextObject> getContextObjects();
	
	IConcept getLeastCommonSuperordinate(Set<IConcept> concepts);
	
	IConcept getOntologicalCommitment();
	
	UpperSemilattice<IConcept, IsA> getOntologicalUpperSemilattice();
	
	//it is guaranteed that the order is the same as getContextObjects();
	List<IConcept> getSingletonConcept();
	
	List<IConcept> getTopologicalSorting();
	
	DirectedAcyclicGraph<IConcept, IsA> getTransitiveReduction();
	
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
