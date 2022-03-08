package com.tregouet.occam.data.automata.transitions.impl;

import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.transitions.IPushdownAutomatonTransition;
import com.tregouet.occam.data.automata.transitions.ITransition;
import com.tregouet.occam.data.automata.transitions.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.automata.transitions.output_config.IPushdownAutomatonOIC;
import com.tregouet.occam.data.languages.generic.AVariable;

public class PushdownAutomatonTransition implements IPushdownAutomatonTransition<IContextualizedProduction, AVariable> {

	private final String name;
	private final IPushdownAutomatonIC<IContextualizedProduction, AVariable> inputConfig;
	private final IPushdownAutomatonOIC<AVariable> outputInternConfig;
	
	public PushdownAutomatonTransition(IPushdownAutomatonIC<IContextualizedProduction, AVariable> inputConfig, 
			IPushdownAutomatonOIC<AVariable> outputInternConfig) {
		name = ITransition.provideName();
		this.inputConfig = inputConfig;
		this.outputInternConfig = outputInternConfig;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IPushdownAutomatonIC<IContextualizedProduction, AVariable> getInputConfiguration() {
		return inputConfig;
	}

	@Override
	public IPushdownAutomatonOIC<AVariable> getOutputInternConfiguration() {
		return outputInternConfig;
	}

}
