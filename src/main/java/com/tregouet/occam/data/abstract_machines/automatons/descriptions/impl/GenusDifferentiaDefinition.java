package com.tregouet.occam.data.abstract_machines.automatons.descriptions.impl;

import java.util.List;

import com.tregouet.occam.data.abstract_machines.automatons.descriptions.IGenusDifferentiaDefinition;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transition_rules.IConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transition_rules.ITransitionRule;

public class GenusDifferentiaDefinition extends IGenusDifferentiaDefinition {

	private static final long serialVersionUID = -2693542568124185602L;
	private final IState speciesState;
	private final IState genusState;
	private final List<IConjunctiveTransition> differentiae;
	
	public GenusDifferentiaDefinition(IState species, IState genus, List<IConjunctiveTransition> differentiae) {
		this.speciesState = species;
		this.genusState = genus;
		this.differentiae = differentiae;
	}

	@Override
	public List<IConjunctiveTransition> getDifferentiae() {
		return differentiae;
	}

	@Override
	public IState getGenusState() {
		return genusState;
	}

	@Override
	public IState getSource() {
		return speciesState;
	}
	
	@Override
	public IState getSpeciesState() {
		return speciesState;
	}
	
	@Override
	public IState getTarget() {
		return genusState;
	}
	
	@Override
	public boolean isReframer() {
		for (ITransitionRule differentia : differentiae)
			if (differentia.isReframer())
				return true;
		return false;
	}

}
