package com.tregouet.occam.data.automata.machines.deprec;

import java.util.List;

import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.automata.transitions.ITransition;

public class GenusDifferentiaDefinition_dep extends IGenusDifferentiaDefinition_dep {

	private static final long serialVersionUID = -2693542568124185602L;
	private final IState speciesState;
	private final IState genusState;
	private final List<IConjunctiveTransition> differentiae;
	
	public GenusDifferentiaDefinition_dep(IState species, IState genus, List<IConjunctiveTransition> differentiae) {
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
		for (ITransition differentia : differentiae)
			if (differentia.isReframer())
				return true;
		return false;
	}

}
