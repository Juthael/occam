package com.tregouet.occam.data.concepts.impl;

import java.util.List;

import com.tregouet.occam.data.abstract_machines.transitions.ITransition;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IGenusDifferentiaDefinition;

public class GenusDifferentiaDefinition extends IGenusDifferentiaDefinition {

	private static final long serialVersionUID = -2693542568124185602L;
	private final IConcept species;
	private final IConcept genus;
	private final List<ITransition> differentiae;
	
	public GenusDifferentiaDefinition(IConcept species, IConcept genus, List<ITransition> differentiae) {
		this.species = species;
		this.genus = genus;
		this.differentiae = differentiae;
	}

	@Override
	public List<ITransition> getDifferentiae() {
		return differentiae;
	}

	@Override
	public IConcept getGenus() {
		return genus;
	}

	@Override
	public IConcept getSpecies() {
		return species;
	}
	
	@Override
	public IConcept getSource() {
		return species;
	}
	
	@Override
	public IConcept getTarget() {
		return genus;
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

	@Override
	public boolean isReframer() {
		for (ITransition differentia : differentiae)
			if (differentia.isReframer())
				return true;
		return false;
	}	

}
