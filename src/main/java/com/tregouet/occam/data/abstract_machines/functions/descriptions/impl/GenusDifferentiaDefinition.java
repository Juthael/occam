package com.tregouet.occam.data.abstract_machines.functions.descriptions.impl;

import java.util.List;

import com.tregouet.occam.data.abstract_machines.functions.descriptions.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transitions.ITransition;

public class GenusDifferentiaDefinition extends IGenusDifferentiaDefinition {

	private static final long serialVersionUID = -2693542568124185602L;
	private final IState species;
	private final IState genus;
	private final List<ITransition> differentiae;
	
	public GenusDifferentiaDefinition(IState species, IState genus, List<ITransition> differentiae) {
		this.species = species;
		this.genus = genus;
		this.differentiae = differentiae;
	}

	@Override
	public List<ITransition> getDifferentiae() {
		return differentiae;
	}

	@Override
	public IState getGenus() {
		return genus;
	}

	@Override
	public IState getSpecies() {
		return species;
	}
	
	@Override
	public IState getSource() {
		return species;
	}
	
	@Override
	public IState getTarget() {
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
