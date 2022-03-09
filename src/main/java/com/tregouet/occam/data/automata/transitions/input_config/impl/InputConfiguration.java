package com.tregouet.occam.data.automata.transitions.input_config.impl;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transitions.input_config.IInputConfiguration;

public class InputConfiguration<InputSymbol extends ISymbol> implements IInputConfiguration<InputSymbol> {
	
	private final IState inputState;
	private final InputSymbol inputSymbol;
	
	public InputConfiguration(IState inputState, InputSymbol inputSymbol) {
		this.inputState = inputState;
		this.inputSymbol = inputSymbol;
	}
	
	@Override
	public IState getInputState() {
		return inputState;
	}

	@Override
	public InputSymbol getInputSymbol() {
		return inputSymbol;
	}

}
