package com.tregouet.occam.data.denotations.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import com.tregouet.occam.alg.concepts_gen.IConceptTreeSupplier;
import com.tregouet.occam.alg.concepts_gen.IConceptsConstructionManager;
import com.tregouet.occam.alg.concepts_gen.IConstrainedConceptTreeSupplier;
import com.tregouet.occam.alg.concepts_gen.impl.DenotationSetsTreeSupplier;
import com.tregouet.occam.data.denotations.IConcept;
import com.tregouet.occam.data.denotations.IConcepts;
import com.tregouet.occam.data.denotations.IContextObject;
import com.tregouet.occam.data.denotations.IExtentStructureConstraint;
import com.tregouet.occam.data.denotations.IIsA;
import com.tregouet.tree_finder.data.UpperSemilattice;

public class Concepts implements IConcepts {
	
	private final List<IContextObject> objects;
	private final DirectedAcyclicGraph<IConcept, IIsA> lattice;
	private final UpperSemilattice<IConcept, IIsA> upperSemilattice;
	private final IConcept ontologicalCommitment;
	private final List<IConcept> topologicalOrder;
	private final IConcept truism;
	private final List<IConcept> objectConcepts;
	private final IConcept absurdity;
	
	public Concepts(Collection<IContextObject> objects) {
		IConceptsConstructionManager manager = IConceptsConstructionManager.getInstance();
		this.objects = manager.getObjects();
		lattice = manager.getLattice();
		upperSemilattice = manager.getUpperSemilattice();
		ontologicalCommitment = manager.getOntologicalCommitment();
		topologicalOrder = manager.getTopologicalOrder();
		truism = manager.getTruism();
		objectConcepts = manager.getObjectConcepts();
		absurdity = manager.getAbsurdity();
	}

	@Override
	public boolean areA(List<IConcept> concepts, IConcept concept) {
		boolean areA = true;
		int dSIndex = 0;
		while (areA && dSIndex < concepts.size()) {
			areA = isA(concepts.get(dSIndex), concept);
			dSIndex++;
		}
		return areA;
	}

	@Override
	public IConcept getAbsurdity() {
		return absurdity;
	}

	@Override
	public IConceptTreeSupplier getConceptTreeSupplier() throws IOException {
		return new DenotationSetsTreeSupplier(upperSemilattice, ontologicalCommitment);
	}

	@Override
	public DirectedAcyclicGraph<IConcept, IIsA> getLatticeOfConcepts() {
		return lattice;
	}

	@Override
	public IConcept getConceptWithExtent(Set<IContextObject> extent) {
		if (extent.containsAll(objects))
			return truism;
		for (IConcept concept : topologicalOrder) {
			if (concept.getExtent().equals(extent))
				return concept;
		}
		return null;
	}

	@Override
	public IConstrainedConceptTreeSupplier getConstrainedConceptTreeSupplier(
			IExtentStructureConstraint constraint) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IContextObject> getContextObjects() {
		return objects;
	}

	@Override
	public IConcept getLeastCommonSuperordinate(Set<IConcept> concepts) {
		if (concepts.isEmpty())
			return null;
		List<IConcept> denotSetList = removeSubCategories(concepts);
		if (denotSetList.size() == 1)
			return denotSetList.get(0);
		IConcept leastCommonSuperordinate = null;
		ListIterator<IConcept> denotSetIterator = topologicalOrder.listIterator(topologicalOrder.size());
		boolean abortSearch = false;
		while (denotSetIterator.hasPrevious() && !abortSearch) {
			IConcept current = denotSetIterator.previous();
			if (areA(denotSetList, current))
				leastCommonSuperordinate = current;
			else if (concepts.contains(current))
				abortSearch = true;
		}
		return leastCommonSuperordinate;		
	}

	@Override
	public IConcept getOntologicalCommitment() {
		return ontologicalCommitment;
	}
	
	@Override
	public UpperSemilattice<IConcept, IIsA> getOntologicalUpperSemilattice() {
		return upperSemilattice;
	}
	
	@Override
	public List<IConcept> getObjectConcepts() {
		return objectConcepts;
	}	
	
	@Override
	public List<IConcept> getTopologicalSorting() {
		return topologicalOrder;
	}
	
	@Override
	public DirectedAcyclicGraph<IConcept, IIsA> getTransitiveReduction() {
		return upperSemilattice;
	}
	
	@Override
	public IConcept getTruism() {
		return truism;
	}

	@Override
	public boolean isA(IConcept denotationSet1, IConcept denotationSet2) {
		boolean isA = false;
		if (topologicalOrder.indexOf(denotationSet1) < topologicalOrder.indexOf(denotationSet2)) {
			BreadthFirstIterator<IConcept, IIsA> iterator = 
					new BreadthFirstIterator<>(upperSemilattice, denotationSet1);
			iterator.next();
			while (!isA && iterator.hasNext())
				isA = denotationSet2.equals(iterator.next());
		}
		return isA;		
	}

	@Override
	public boolean isADirectSubordinateOf(IConcept denotationSet1, IConcept denotationSet2) {
		return (upperSemilattice.getEdge(denotationSet1, denotationSet2) != null);
	}	

	private List<IConcept> removeSubCategories(Set<IConcept> concepts) {
		List<IConcept> denotSetList = new ArrayList<>(concepts);
		for (int i = 0 ; i < denotSetList.size() - 1 ; i++) {
			IConcept iDenotSet = denotSetList.get(i);
			for (int j = i+1 ; j < denotSetList.size() ; j++) {
				IConcept jDenotSet = denotSetList.get(j);
				if (isA(iDenotSet, jDenotSet))
					concepts.remove(iDenotSet);
				else if (isA(jDenotSet, iDenotSet))
					concepts.remove(jDenotSet);
			}
		}
		return new ArrayList<>(concepts);
	}

}
