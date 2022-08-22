package com.tregouet.occam.alg.builders.pb_space.metrics.matrices.symmetrical_sim.impl.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.data.modules.categorization.transitions.AProblemStateTransition;
import com.tregouet.occam.data.representations.IRepresentation;

public class References {

	private List<IRepresentation> refList = new ArrayList<>();
	private DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph;

	public References(DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> problemGraph) {
		this.problemGraph = problemGraph;
	}

	public boolean addNew(IRepresentation representation) {
		if (refList.isEmpty()) {
			refList.add(representation);
			return true;
		}
		ListIterator<IRepresentation> refIte = refList.listIterator();
		while (refIte.hasNext()) {
			IRepresentation nextRef = refIte.next();
			Integer comparison = nextRef.compareTo(representation);
			if (comparison != null){
				if (comparison < 1)
					return false;
				if (comparison > 1)
					refIte.remove();
			}
		}
		refList.add(representation);
		return true;
	}

	public boolean clearPreviousAddNew(IRepresentation representation) {
		refList.clear();
		return addNew(representation);
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		Iterator<IRepresentation> refIte = refList.iterator();
		while (refIte.hasNext()) {
			IRepresentation nextRepresentation = refIte.next();
			boolean isLeaf = problemGraph.outDegreeOf(nextRepresentation) == 0;
			if (!isLeaf)
				sB.append("[");
			sB.append(Integer.toString(nextRepresentation.iD()));
			if (!isLeaf)
				sB.append(")");
			if (refIte.hasNext())
				sB.append(", ");
		}
		return sB.toString();
	}
}
