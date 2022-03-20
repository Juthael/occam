package com.tregouet.occam.data.preconcepts.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import com.tregouet.occam.alg.generators.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.generators.preconcepts.IPreconceptTreeSupplier;
import com.tregouet.occam.alg.generators.preconcepts.IPreconceptsConstructionManager;
import com.tregouet.occam.alg.generators.preconcepts.impl.PreconceptTreeSupplier;
import com.tregouet.occam.data.preconcepts.IContextObject;
import com.tregouet.occam.data.preconcepts.IIsA;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.preconcepts.IPreconcepts;
import com.tregouet.tree_finder.data.UpperSemilattice;

public class Preconcepts implements IPreconcepts {
	
	private final List<IContextObject> objects;
	private final DirectedAcyclicGraph<IPreconcept, IIsA> lattice;
	private final UpperSemilattice<IPreconcept, IIsA> upperSemilattice;
	private final List<IPreconcept> topologicalOrder;
	private final IPreconcept ontologicalCommitment;
	private final IPreconcept truism;
	private final List<IPreconcept> objectPreconcepts;
	private final IPreconcept absurdity;
	
	public Preconcepts(Collection<IContextObject> objects) {
		IPreconceptsConstructionManager manager = 
				GeneratorsAbstractFactory.INSTANCE.getPreconceptsConstructionManager();
		this.objects = manager.getObjects();
		lattice = manager.getLattice();
		upperSemilattice = manager.getUpperSemilattice();
		ontologicalCommitment = manager.getOntologicalCommitment();
		topologicalOrder = manager.getTopologicalOrder();
		truism = manager.getTruism();
		objectPreconcepts = manager.getObjectPreconcepts();
		absurdity = manager.getAbsurdity();
	}

	@Override
	public boolean areA(List<IPreconcept> preconcepts, IPreconcept preconcept) {
		boolean areA = true;
		int dSIndex = 0;
		while (areA && dSIndex < preconcepts.size()) {
			areA = isA(preconcepts.get(dSIndex), preconcept);
			dSIndex++;
		}
		return areA;
	}

	@Override
	public IPreconcept getAbsurdity() {
		return absurdity;
	}

	@Override
	public IPreconceptTreeSupplier getConceptTreeSupplier() throws IOException {
		return new PreconceptTreeSupplier(upperSemilattice, ontologicalCommitment);
	}

	@Override
	public DirectedAcyclicGraph<IPreconcept, IIsA> getLatticeOfConcepts() {
		return lattice;
	}

	@Override
	public IPreconcept getConceptWithExtent(Set<IContextObject> extent) {
		if (extent.containsAll(objects))
			return truism;
		for (IPreconcept preconcept : topologicalOrder) {
			if (preconcept.getExtent().equals(extent))
				return preconcept;
		}
		return null;
	}

	@Override
	public List<IContextObject> getContextObjects() {
		return objects;
	}

	@Override
	public IPreconcept getLeastCommonSuperordinate(Set<IPreconcept> preconcepts) {
		if (preconcepts.isEmpty())
			return null;
		List<IPreconcept> denotSetList = removeSubCategories(preconcepts);
		if (denotSetList.size() == 1)
			return denotSetList.get(0);
		IPreconcept leastCommonSuperordinate = null;
		ListIterator<IPreconcept> denotSetIterator = topologicalOrder.listIterator(topologicalOrder.size());
		boolean abortSearch = false;
		while (denotSetIterator.hasPrevious() && !abortSearch) {
			IPreconcept current = denotSetIterator.previous();
			if (areA(denotSetList, current))
				leastCommonSuperordinate = current;
			else if (preconcepts.contains(current))
				abortSearch = true;
		}
		return leastCommonSuperordinate;		
	}

	@Override
	public IPreconcept getOntologicalCommitment() {
		return ontologicalCommitment;
	}
	
	@Override
	public UpperSemilattice<IPreconcept, IIsA> getOntologicalUpperSemilattice() {
		return upperSemilattice;
	}
	
	@Override
	public List<IPreconcept> getObjectConcepts() {
		return objectPreconcepts;
	}	
	
	@Override
	public List<IPreconcept> getTopologicalSorting() {
		return topologicalOrder;
	}
	
	@Override
	public DirectedAcyclicGraph<IPreconcept, IIsA> getTransitiveReduction() {
		return upperSemilattice;
	}
	
	@Override
	public IPreconcept getTruism() {
		return truism;
	}

	@Override
	public boolean isA(IPreconcept denotationSet1, IPreconcept denotationSet2) {
		boolean isA = false;
		if (topologicalOrder.indexOf(denotationSet1) < topologicalOrder.indexOf(denotationSet2)) {
			BreadthFirstIterator<IPreconcept, IIsA> iterator = 
					new BreadthFirstIterator<>(upperSemilattice, denotationSet1);
			iterator.next();
			while (!isA && iterator.hasNext())
				isA = denotationSet2.equals(iterator.next());
		}
		return isA;		
	}

	@Override
	public boolean isADirectSubordinateOf(IPreconcept denotationSet1, IPreconcept denotationSet2) {
		return (upperSemilattice.getEdge(denotationSet1, denotationSet2) != null);
	}	

	private List<IPreconcept> removeSubCategories(Set<IPreconcept> preconcepts) {
		List<IPreconcept> denotSetList = new ArrayList<>(preconcepts);
		for (int i = 0 ; i < denotSetList.size() - 1 ; i++) {
			IPreconcept iDenotSet = denotSetList.get(i);
			for (int j = i+1 ; j < denotSetList.size() ; j++) {
				IPreconcept jDenotSet = denotSetList.get(j);
				if (isA(iDenotSet, jDenotSet))
					preconcepts.remove(iDenotSet);
				else if (isA(jDenotSet, iDenotSet))
					preconcepts.remove(jDenotSet);
			}
		}
		return new ArrayList<>(preconcepts);
	}

}
