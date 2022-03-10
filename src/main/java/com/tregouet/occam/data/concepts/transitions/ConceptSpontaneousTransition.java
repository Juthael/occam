package com.tregouet.occam.data.concepts.transitions;

import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.automata.transitions.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.languages.generic.AVariable;

public class ConceptSpontaneousTransition extends ConceptTransition
		implements IPushdownAutomatonIC<IContextualizedProduction, AVariable> {

	public ConceptSpontaneousTransition(ConceptTransitionIC inputConfig, ConceptTransitionOIC outputInternConfig) {
		super(inputConfig, outputInternConfig);
		// TODO Auto-generated constructor stub
	}

	@Override
	public IState getInputState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContextualizedProduction getInputSymbol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AVariable getInputStackSymbol() {
		// TODO Auto-generated method stub
		return null;
	}

}
