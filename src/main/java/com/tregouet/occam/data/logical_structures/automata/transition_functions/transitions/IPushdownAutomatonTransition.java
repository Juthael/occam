package com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions;

import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.logical_structures.automata.transition_functions.transitions.output_config.IPushdownAutomatonOIC;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;

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
