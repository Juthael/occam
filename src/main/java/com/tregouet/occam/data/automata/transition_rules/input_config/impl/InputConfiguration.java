package com.tregouet.occam.data.automata.transition_rules.input_config.impl;

import java.util.Objects;

import com.tregouet.occam.data.alphabets.productions.IProduction;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transition_rules.input_config.IInputConfiguration;

public class InputConfiguration implements IInputConfiguration<IProduction> {
	
	private final IState inputState;
	private final IProduction inputSymbol;
	
	public InputConfiguration(IState inputState, IProduction inputSymbol) {
		this.inputState = inputState;
		this.inputSymbol = inputSymbol;
	}
	
	@Override
	public IState getInputState() {
		return inputState;
	}

	@Override
	public IProduction getInputSymbol() {
		return inputSymbol;
	}

	@Override
	public int hashCode() {
		return Objects.hash(inputState, inputSymbol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InputConfiguration other = (InputConfiguration) obj;
		return Objects.equals(inputState, other.inputState) && Objects.equals(inputSymbol, other.inputSymbol);
	}

}
