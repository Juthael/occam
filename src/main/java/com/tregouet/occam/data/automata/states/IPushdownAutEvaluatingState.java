package com.tregouet.occam.data.automata.states;

import com.tregouet.occam.data.alphabets.ISymbol;
import com.tregouet.occam.data.automata.transitions.IPushdownAutomatonTransition;

public interface IPushdownAutEvaluatingState<
	InputSymbol extends ISymbol, 
	StackSymbol extends ISymbol,  
	Transition extends IPushdownAutomatonTransition<InputSymbol, StackSymbol>
	>
	extends IPushdownAutomatonState<InputSymbol, StackSymbol, Transition>{
	
	void loadTransitionRules(Transition transitions);

}
