package com.tregouet.occam.data.operators.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.operators.IBasicOperator;
import com.tregouet.occam.data.operators.IConjunctiveTransition;
import com.tregouet.occam.data.operators.ILambdaExpression;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.IReframer;
import com.tregouet.occam.data.operators.ITransition;
import com.tregouet.occam.transition_function.IState;

public class ConjunctiveTransition implements IConjunctiveTransition {
	
	private String name;
	private List<ITransition> transitions = new ArrayList<>();
	private final IState operatingState;
	private final IState nextState;

	public ConjunctiveTransition(ITransition transition) {
		name = IConjunctiveTransition.provideName();
		transitions.add(transition);
		operatingState = transition.getOperatingState();
		nextState = transition.getNextState();
	}

	@Override
	public boolean addTransition(ITransition transition) {
		if (this.operatingState.equals(transition.getOperatingState()) 
				&& this.nextState.equals(transition.getNextState())) {
			transitions.add(transition);
		}
		return false;
	}

	@Override
	public List<ITransition> getComponents() {
		return transitions;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IState getNextState() {
		return nextState;
	}

	@Override
	public IState getOperatingState() {
		return operatingState;
	}

	@Override
	public boolean isBlank() {
		for (ITransition transition : transitions) {
			if (transition instanceof IReframer)
				return false;
			else {
				IOperator op = (IOperator) transition;
				if (!op.isBlank())
					return false;
			}
		}
		return true;
	}

	@Override
	public IIntentAttribute operateOn(IIntentAttribute input) {
		IIntentAttribute output = null;
		int opIdx = 0;
		while (output == null && opIdx < transitions.size()) {
			ITransition currentTrans = transitions.get(opIdx);
			if (currentTrans instanceof IOperator)
				output = ((IOperator) currentTrans).operateOn(input);
			opIdx++;
		}
		return output;
	}

	@Override
	public List<IProduction> operation() {
		List<IProduction> operations = new ArrayList<>();
		for (ITransition transition : transitions) {
			if (transition instanceof IOperator)
				operations.addAll(((IOperator) transition).operation());
		}
		return operations;
	}

	@Override
	public List<ILambdaExpression> semantics() {
		List<ILambdaExpression> semantics = new ArrayList<>();
		for (ITransition transition : transitions) {
			if (transition instanceof IOperator)
				semantics.addAll(((IOperator) transition).semantics());
		}
		return semantics;
	}

}
