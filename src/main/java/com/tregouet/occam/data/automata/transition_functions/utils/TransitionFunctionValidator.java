package com.tregouet.occam.data.automata.transition_functions.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import com.tregouet.occam.data.automata.machines.IAutomaton;
import com.tregouet.occam.data.automata.states.IState;

public class TransitionFunctionValidator implements Predicate<IAutomaton> {

	public final static TransitionFunctionValidator INSTANCE = new TransitionFunctionValidator();
	
	private TransitionFunctionValidator() {
	}

	private static boolean everyStateIsTheSourceOfAnInformativeTransition(IAutomaton tF) {
		Set<IState> states = new HashSet<>(tF.getStates());
		states.removeIf(s -> s.getStateType() == IState.OC_STATE);
		for (IConjunctiveTransition operator : tF.getConjunctiveTransitions()) {
			if (!operator.isBlank()) 
				states.remove(operator.getInputState());
		}
		return (states.isEmpty());
	}
	
	@Override
	public boolean test(IAutomaton tF) {
		return everyStateIsTheSourceOfAnInformativeTransition(tF);
	}

}
