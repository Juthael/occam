package com.tregouet.occam.data.representations.topo_order;

import java.util.Comparator;

import com.tregouet.occam.data.representations.IRepresentation;

public class TopoOrderOverReps implements Comparator<IRepresentation> {
	
	public static final TopoOrderOverReps INSTANCE = new TopoOrderOverReps();
	
	private TopoOrderOverReps() {
	}

	@Override
	public int compare(IRepresentation r1, IRepresentation r2) {
		Integer partialComparison = r1.compareTo(r2);
		if (partialComparison != null)
			return partialComparison;
		else return r1.id() - r2.id();
	}

}
