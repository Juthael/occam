package com.tregouet.occam.data.automata.states;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.transitions.ITransition;
import com.tregouet.occam.data.automata.transitions.input_config.IPushdownAutomatonIC;
import com.tregouet.occam.data.automata.transitions.output_config.IPushdownAutomatonOIC;

public interface IPushdownAutomatonState<InputSymbol extends ISymbol, StackSymbol extends ISymbol> extends IState {
	
	void loadTransitionRule(
			ITransition<IPushdownAutomatonIC<InputSymbol, StackSymbol>, IPushdownAutomatonOIC<StackSymbol>, InputSymbol> transition);

}
