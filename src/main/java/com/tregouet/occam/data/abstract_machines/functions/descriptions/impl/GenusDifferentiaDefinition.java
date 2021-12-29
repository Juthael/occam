package com.tregouet.occam.data.abstract_machines.functions.descriptions.impl;

import java.util.List;

import com.tregouet.occam.data.abstract_machines.functions.descriptions.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transitions.ITransition;

public class GenusDifferentiaDefinition extends IGenusDifferentiaDefinition {

	private static final long serialVersionUID = -2693542568124185602L;
	private final IState species;
	private final IState genus;
	private final List<IConjunctiveTransition> differentiae;
	
	public GenusDifferentiaDefinition(IState species, IState genus, List<IConjunctiveTransition> differentiae) {
		this.species = species;
		this.genus = genus;
		this.differentiae = differentiae;
	}

	@Override
	public List<IConjunctiveTransition> getDifferentiae() {
		return differentiae;
	}

	@Override
	public IState getGenus() {
		return genus;
	}

	@Override
	public IState getSource() {
		return species;
	}
	
	@Override
	public IState getSpecies() {
		return species;
	}
	
	@Override
	public IState getTarget() {
		return genus;
	}
	
	@Override
	public boolean isReframer() {
		for (ITransition differentia : differentiae)
			if (differentia.isReframer())
				return true;
		return false;
	}

}
