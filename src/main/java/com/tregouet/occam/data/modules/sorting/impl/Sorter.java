package com.tregouet.occam.data.modules.sorting.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.categorizer.ProblemSpaceExplorer;
import com.tregouet.occam.data.modules.sorting.ISorter;
import com.tregouet.occam.data.modules.sorting.transitions.AProblemStateTransition;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IIsA;

public class Sorter implements ISorter {

	private List<IContextObject> context = null;
	private ProblemSpaceExplorer problemSpaceExplorer = null;
	private IRepresentation activeState = null;

	public Sorter() {
	}

	@Override
	public Boolean develop() {
		try {
			problemSpaceExplorer.develop();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		return true;
	}

	@Override
	public Boolean develop(int representationID) {
		return problemSpaceExplorer.develop(representationID);
	}

	@Override
	public Boolean develop(List<Integer> representationIDs) {
		return problemSpaceExplorer.develop(new HashSet<>(representationIDs));
	}

	@Override
	public Boolean display(int representationID) {
		if (activeState != null && activeState.iD() == representationID)
			return false;
		boolean representationFound = false;
		Iterator<IRepresentation> repIte = problemSpaceExplorer.getProblemSpaceGraph().vertexSet().iterator();
		while (!representationFound && repIte.hasNext()) {
			IRepresentation nextRep = repIte.next();
			if (nextRep.iD() == representationID) {
				activeState = nextRep;
				representationFound = true;
			}
		}
		if (!representationFound)
			return null;
		return true;
	}

	@Override
	public IRepresentation getActiveRepresentation() {
		return activeState;
	}

	@Override
	public List<IContextObject> getContext() {
		return context;
	}

	@Override
	public DirectedAcyclicGraph<IConcept, IIsA> getLatticeOfConcepts() {
		return problemSpaceExplorer.getLatticeOfConcepts();
	}

	@Override
	public DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> getProblemSpaceGraph() {
		return problemSpaceExplorer.getProblemSpaceGraph();
	}

	@Override
	public ISorter process(Collection<IContextObject> context) {
		this.context = new ArrayList<>(context);
		this.context.sort((x, y) -> Integer.compare(x.iD(), y.iD()));
		problemSpaceExplorer = ISorter.problemSpaceExplorer().initialize(this.context);
		return this;
	}

	@Override
	public Boolean restrictTo(Set<Integer> representationIDs) {
		return problemSpaceExplorer.restrictTo(representationIDs);
	}

}
