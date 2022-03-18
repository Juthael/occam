package com.tregouet.occam.data.automata.transition_functions.transitions;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.transition_functions.transitions.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.automata.transition_functions.transitions.output_config.IPushdownAutomatonOIC;

public interface IPushdownAutomatonTransition<
	InputSymbol extends ISymbol, 
	StackSymbol extends ISymbol,
	InputConfig extends IPushdownAutomatonIC<InputSymbol, StackSymbol>,
	OutputConfig extends IPushdownAutomatonOIC<StackSymbol>
	>
	extends ITransition<InputSymbol, InputConfig, OutputConfig>  {
	
	@Override
	boolean equals(Object o);
	
	@Override
	int hashCode();

}
