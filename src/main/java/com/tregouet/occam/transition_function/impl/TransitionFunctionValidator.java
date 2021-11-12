package com.tregouet.occam.transition_function.impl;

import java.util.List;
import java.util.function.Predicate;

import com.tregouet.occam.data.operators.IConjunctiveOperator;
import com.tregouet.occam.transition_function.IState;
import com.tregouet.occam.transition_function.ITransitionFunction;

public class TransitionFunctionValidator implements Predicate<ITransitionFunction> {

	public final static TransitionFunctionValidator INSTANCE = new TransitionFunctionValidator();
	
	private TransitionFunctionValidator() {
	}

	@Override
	public boolean test(ITransitionFunction tF) {
		return transitsThroughEveryState(tF);
	}
	
	private static boolean transitsThroughEveryState(ITransitionFunction tF) {
		List<IState> states = tF.getStates();
		for (IConjunctiveOperator operator : tF.getConjunctiveTransitions()) {
			states.remove(operator.getOperatingState());
			states.remove(operator.getNextState());
		}
		return states.isEmpty();
	}

}
