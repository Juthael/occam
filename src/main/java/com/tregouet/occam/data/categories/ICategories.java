package com.tregouet.occam.data.categories;

import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.tree_finder.data.UpperSemilattice;
import com.tregouet.tree_finder.error.InvalidInputException;

public interface ICategories {
	
	@Override
	public int hashCode();
	
	boolean areA(List<ICategory> cats, ICategory cat);
	
	@Override
	boolean equals(Object o);
	
	ICategory getAbsurdity();
	
	DirectedAcyclicGraph<ICategory, DefaultEdge> getCategoryLattice();
	
	IClassificationTreeSupplier getCatTreeSupplier() throws InvalidInputException;
	
	IClassTreeWithConstrainedExtentStructureSupplier getCatTreeSupplier(IExtentStructureConstraint constraint);
	
	/**
	 * If param contains every object in the context, then return truism
	 * @param extent
	 * @return
	 */
	ICategory getCatWithExtent(Set<IContextObject> extent);
	
	List<IContextObject> getContextObjects();
	
	ICategory getLeastCommonSuperordinate(Set<ICategory> categories);
	
	List<ICategory> getObjectCategories();
	
	ICategory getOntologicalCommitment();
	
	UpperSemilattice<ICategory, DefaultEdge> getOntologicalUpperSemilattice();
	
	List<ICategory> getTopologicalSorting();
	
	DirectedAcyclicGraph<ICategory, DefaultEdge> getTransitiveReduction();
	
	ICategory getTruism();
	
	/**
	 * Not a reflexive relation
	 * @param cat1
	 * @param cat2
	 * @return
	 */
	boolean isA(ICategory cat1, ICategory cat2);

	boolean isADirectSubordinateOf(ICategory cat1, ICategory cat2);

}
