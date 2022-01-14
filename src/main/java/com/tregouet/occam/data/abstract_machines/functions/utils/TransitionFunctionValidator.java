package com.tregouet.occam.data.abstract_machines.functions.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import com.tregouet.occam.data.abstract_machines.functions.ITransitionFunction;
import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transition_rules.IConjunctiveTransition;

public class TransitionFunctionValidator implements Predicate<ITransitionFunction> {

	public final static TransitionFunctionValidator INSTANCE = new TransitionFunctionValidator();
	
	private TransitionFunctionValidator() {
	}

	private static boolean everyStateIsTheSourceOfAnInformativeTransition(ITransitionFunction tF) {
		Set<IState> states = new HashSet<>(tF.getStates());
		states.removeIf(s -> s.getStateType() == IState.OC_STATE);
		for (IConjunctiveTransition operator : tF.getConjunctiveTransitions()) {
			if (!operator.isBlank()) 
				states.remove(operator.getInputState());
		}
		return (states.isEmpty());
	}
	
	@Override
	public boolean test(ITransitionFunction tF) {
		return everyStateIsTheSourceOfAnInformativeTransition(tF);
	}

}
