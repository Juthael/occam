package com.tregouet.occam.data.automata.transition_rules.output_config.impl;

import com.tregouet.occam.data.alphabets.operators.IOperator;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transition_rules.output_config.IPushdownTransducerOIC;
import com.tregouet.occam.data.languages.generic.AVariable;

public class PushdownTransducerOIC extends PushdownAutomatonOIC implements IPushdownTransducerOIC<AVariable, IOperator> {
	
	private final IOperator outputSymbol;
	
	public PushdownTransducerOIC(IState outputState, AVariable nextStackSymbol, IOperator outputSymbol) {
		super(outputState, nextStackSymbol);
		this.outputSymbol = outputSymbol;
	}

	@Override
	public IOperator getOutputSymbol() {
		return outputSymbol;
	}

}
