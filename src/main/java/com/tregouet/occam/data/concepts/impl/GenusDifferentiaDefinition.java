package com.tregouet.occam.data.concepts.impl;

import java.util.List;

import com.tregouet.occam.data.abstract_machines.transitions.ITransition;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IGenusDifferentiaDefinition;

public class GenusDifferentiaDefinition extends IGenusDifferentiaDefinition {

	private static final long serialVersionUID = -2693542568124185602L;
	private final List<ITransition> differentiae;
	
	public GenusDifferentiaDefinition(List<ITransition> differentiae) {
		this.differentiae = differentiae;
	}

	@Override
	public List<ITransition> getDifferentiae() {
		return differentiae;
	}

	@Override
	public IConcept getGenus() {
		return (IConcept) getTarget();
	}

	@Override
	public IConcept getSpecies() {
		return (IConcept) getSource();
	}
	
	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		String nL = System.lineSeparator();
		int nbOfDiff = differentiae.size();
		for (int i = 0 ; i < nbOfDiff ; i++) {
			sB.append(differentiae.get(i).toString());
			if (i < nbOfDiff - 1)
				sB.append(nL);
		}
		return sB.toString();
	}	

}
