package com.tregouet.occam.data.abstract_machines.transition_rules.impl;

import java.util.Objects;

import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transition_rules.IInputConfiguration;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.specific.IBasicProductionAsEdge;

public class InputConfiguration implements IInputConfiguration {

	private final IState inputState;
	private final IBasicProductionAsEdge inputSymbol;
	private final AVariable stackSymbol;
	
	public InputConfiguration(IState inputState, IBasicProductionAsEdge inputSymbol, AVariable stackSymbol) {
		this.inputState = inputState;
		this.inputSymbol = inputSymbol;
		this.stackSymbol = stackSymbol;
	}
	
	@Override
	public IState getInputState() {
		return inputState;
	}

	@Override
	public IBasicProductionAsEdge getInputSymbol() {
		return inputSymbol;
	}

	@Override
	public AVariable getInputStackSymbol() {
		return stackSymbol;
	}

	@Override
	public int hashCode() {
		return Objects.hash(inputState, inputSymbol, stackSymbol);
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
		return Objects.equals(inputState, other.inputState) && Objects.equals(inputSymbol, other.inputSymbol)
				&& Objects.equals(stackSymbol, other.stackSymbol);
	}
	
	

}
