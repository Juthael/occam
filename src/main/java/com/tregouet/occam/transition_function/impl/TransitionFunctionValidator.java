package com.tregouet.occam.transition_function.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import com.tregouet.occam.data.concepts.impl.IsA;
import com.tregouet.occam.data.operators.IConjunctiveTransition;
import com.tregouet.occam.io.output.utils.Visualizer;
import com.tregouet.occam.transition_function.IState;
import com.tregouet.occam.transition_function.ITransitionFunction;
import com.tregouet.occam.transition_function.TransitionFunctionGraphType;

public class TransitionFunctionValidator implements Predicate<ITransitionFunction> {

	public final static TransitionFunctionValidator INSTANCE = new TransitionFunctionValidator();
	
	private TransitionFunctionValidator() {
	}

	@Override
	public boolean test(ITransitionFunction tF) {
		return noBlankState(tF);
	}
	
	private static boolean noBlankState(ITransitionFunction tF) {
		//HERE
		/*
		try {
			Visualizer.visualizeTransitionFunction(tF, "211209_errorSearch", TransitionFunctionGraphType.FINITE_AUTOMATON);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//HERE
		Set<IState> states = new HashSet<>(tF.getStates());
		states.removeIf(s -> s.getStateType() == IState.OC_STATE);
		for (IConjunctiveTransition operator : tF.getConjunctiveTransitions()) {
			if (!operator.isBlank()) 
				states.remove(operator.getOperatingState());
		}
		return (states.isEmpty());
	}

}
