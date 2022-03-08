package com.tregouet.occam.data.automata.transition_rules;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.transition_rules.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.automata.transition_rules.output_config.IPushdownAutomatonOIC;

public interface IPushdownAutomatonTransition<InputSymbol extends ISymbol, StackSymbol extends ISymbol> 
	extends ITransition<IPushdownAutomatonIC<InputSymbol, StackSymbol>, IPushdownAutomatonOIC<StackSymbol>, InputSymbol> {

}
