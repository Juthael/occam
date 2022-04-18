package com.tregouet.occam.data.representations.concepts.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IContextObject;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedUpperSemilattice;

public class ConceptLattice implements IConceptLattice {

	private final List<IContextObject> objects;
	private final DirectedAcyclicGraph<IConcept, IIsA> lattice;
	private final InvertedUpperSemilattice<IConcept, IIsA> invertedUpperSemilattice;
	private final List<IConcept> topologicalOrder;
	private final IConcept ontologicalCommitment;
	private final IConcept truism;
	private final List<IConcept> particulars;
	private final IConcept absurdity;

	public ConceptLattice(List<IContextObject> objects, DirectedAcyclicGraph<IConcept, IIsA> lattice,
			InvertedUpperSemilattice<IConcept, IIsA> upperSemilattice, List<IConcept> topologicalOrder,
			IConcept ontologicalCommitment, IConcept truism, List<IConcept> particulars, IConcept absurdity) {
		this.objects = objects;
		this.lattice = lattice;
		this.invertedUpperSemilattice = upperSemilattice;
		this.topologicalOrder = topologicalOrder;
		this.ontologicalCommitment = ontologicalCommitment;
		this.truism = truism;
		this.particulars = particulars;
		this.absurdity = absurdity;
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
	public List<IContextObject> getContextObjects() {
		return objects;
	}

	@Override
	public DirectedAcyclicGraph<IConcept, IIsA> getLatticeOfConcepts() {
		return lattice;
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
	public List<IConcept> getObjectConcepts() {
		return particulars;
	}

	@Override
	public IConcept getOntologicalCommitment() {
		return ontologicalCommitment;
	}

	@Override
	public InvertedUpperSemilattice<IConcept, IIsA> getOntologicalUpperSemilattice() {
		return invertedUpperSemilattice;
	}

	@Override
	public List<IConcept> getTopologicalSorting() {
		return topologicalOrder;
	}

	@Override
	public DirectedAcyclicGraph<IConcept, IIsA> getTransitiveReduction() {
		return invertedUpperSemilattice;
	}

	@Override
	public IConcept getTruism() {
		return truism;
	}

	@Override
	public boolean isA(IConcept denotationSet1, IConcept denotationSet2) {
		boolean isA = false;
		if (topologicalOrder.indexOf(denotationSet1) < topologicalOrder.indexOf(denotationSet2)) {
			BreadthFirstIterator<IConcept, IIsA> iterator = new BreadthFirstIterator<>(invertedUpperSemilattice,
					denotationSet1);
			iterator.next();
			while (!isA && iterator.hasNext())
				isA = denotationSet2.equals(iterator.next());
		}
		return isA;
	}

	@Override
	public boolean isADirectSubordinateOf(IConcept denotationSet1, IConcept denotationSet2) {
		return (invertedUpperSemilattice.getEdge(denotationSet1, denotationSet2) != null);
	}

	private List<IConcept> removeSubCategories(Set<IConcept> concepts) {
		List<IConcept> denotSetList = new ArrayList<>(concepts);
		for (int i = 0; i < denotSetList.size() - 1; i++) {
			IConcept iDenotSet = denotSetList.get(i);
			for (int j = i + 1; j < denotSetList.size(); j++) {
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
