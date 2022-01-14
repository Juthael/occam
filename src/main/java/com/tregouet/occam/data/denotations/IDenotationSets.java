package com.tregouet.occam.data.denotations;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.denotation_sets_gen.IConstrainedDenotationSetsTreeSupplier;
import com.tregouet.occam.alg.denotation_sets_gen.IDenotationSetsTreeSupplier;
import com.tregouet.tree_finder.data.UpperSemilattice;

public interface IDenotationSets {
	
	@Override
	public int hashCode();
	
	boolean areA(List<IDenotationSet> cats, IDenotationSet cat);
	
	@Override
	boolean equals(Object o);
	
	IDenotationSet getAbsurdity();
	
	IDenotationSetsTreeSupplier getDenotationSetsTreeSupplier() throws IOException;
	
	DirectedAcyclicGraph<IDenotationSet, IIsA> getLatticeOfDenotationSets();
	
	/**
	 * If param contains every object in the context, then return truism
	 * @param extent
	 * @return
	 */
	IDenotationSet getDenotationSetWithExtent(Set<IContextObject> extent);
	
	IConstrainedDenotationSetsTreeSupplier getConstrainedDenotationSetsTreeSupplier(IExtentStructureConstraint constraint);
	
	List<IContextObject> getContextObjects();
	
	IDenotationSet getLeastCommonSuperordinate(Set<IDenotationSet> denotationSets);
	
	IDenotationSet getOntologicalCommitment();
	
	UpperSemilattice<IDenotationSet, IIsA> getOntologicalUpperSemilattice();
	
	//it is guaranteed that the order is the same as getContextObjects();
	List<IDenotationSet> getObjectDenotationSets();
	
	List<IDenotationSet> getTopologicalSorting();
	
	DirectedAcyclicGraph<IDenotationSet, IIsA> getTransitiveReduction();
	
	IDenotationSet getTruism();
	
	/**
	 * Not a reflexive relation
	 * @param cat1
	 * @param cat2
	 * @return
	 */
	boolean isA(IDenotationSet cat1, IDenotationSet cat2);

	boolean isADirectSubordinateOf(IDenotationSet cat1, IDenotationSet cat2);

}
