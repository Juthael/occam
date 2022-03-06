package com.tregouet.occam.data.automata.transition_rules.input_config.impl;

import java.util.Objects;

import com.tregouet.occam.data.alphabets.productions.IProduction;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transition_rules.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.languages.generic.AVariable;

public class PushdownAutomatonIC extends InputConfiguration implements IPushdownAutomatonIC<IProduction, AVariable> {

	private final AVariable stackSymbol;
	
	public PushdownAutomatonIC(IState inputState, IProduction inputSymbol, AVariable stackSymbol) {
		super(inputState, inputSymbol);
		this.stackSymbol = stackSymbol;
	}

	@Override
	public AVariable getInputStackSymbol() {
		return stackSymbol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(stackSymbol);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PushdownAutomatonIC other = (PushdownAutomatonIC) obj;
		return Objects.equals(stackSymbol, other.stackSymbol);
	}
	
	

}
