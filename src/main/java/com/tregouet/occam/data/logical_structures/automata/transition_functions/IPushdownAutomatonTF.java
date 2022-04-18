package com.tregouet.occam.data.logical_structures.automata.transition_functions;

import java.util.Set;

import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.IPushdownAutomatonTransition;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.output_config.IPushdownAutomatonOIC;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;

public interface IPushdownAutomatonTF<
	InputSymbol extends ISymbol,
	StackSymbol extends ISymbol,
	InputConfig extends IPushdownAutomatonIC<InputSymbol, StackSymbol>,
	OutputConfig extends IPushdownAutomatonOIC<StackSymbol>,
	Transition extends IPushdownAutomatonTransition<InputSymbol, StackSymbol, InputConfig, OutputConfig>
	>
	extends ITransitionFunction<InputSymbol, InputConfig, OutputConfig, Transition> {

	@Override
	boolean equals(Object o);

	StackSymbol getInitialStackSymbol();

	Set<StackSymbol> getStackAlphabet();

	@Override
	int hashCode();

}
