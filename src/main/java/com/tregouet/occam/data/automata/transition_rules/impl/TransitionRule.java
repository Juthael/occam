package com.tregouet.occam.data.automata.transition_rules.impl;

import java.util.Objects;

import com.tregouet.occam.data.alphabets.productions.IProduction;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transition_rules.ITransitionRule;
import com.tregouet.occam.data.automata.transition_rules.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.automata.transition_rules.output_config.IOutputInternConfiguration;
import com.tregouet.occam.data.languages.generic.AVariable;

public class TransitionRule implements ITransitionRule {

	private final String name;
	private final IPushdownAutomatonIC input;
	private final IOutputInternConfiguration output;
	
	public TransitionRule(IPushdownAutomatonIC input, IOutputInternConfiguration output) {
		name = ITransitionRule.provideName();
		this.input = input;
		this.output = output;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public IState getOutputState() {
		return output.getOutputState();
	}

	@Override
	public IState getInputState() {
		return input.getInputState();
	}

	@Override
	public IProduction getInputSymbol() {
		return input.getInputSymbol();
	}

	@Override
	public AVariable getInputStackSymbol() {
		return input.getInputStackSymbol();
	}

	@Override
	public AVariable getOutputStackSymbol() {
		return output.getOutputStackSymbol();
	}

	@Override
	public IPushdownAutomatonIC getInputConfiguration() {
		return input;
	}

	@Override
	public IOutputInternConfiguration getOutputInternConfiguration() {
		return output;
	}

	@Override
	public boolean isBlank() {
		return input.getInputSymbol().isEpsilon();
	}

	@Override
	public boolean isReframer() {
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(input, name, output);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransitionRule other = (TransitionRule) obj;
		return Objects.equals(name, other.name) && Objects.equals(input, other.input) 
				&& Objects.equals(output, other.output);
	}

}
