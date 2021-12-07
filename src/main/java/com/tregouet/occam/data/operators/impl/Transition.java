package com.tregouet.occam.data.operators.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.operators.ILambdaExpression;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.data.operators.ITransition;
import com.tregouet.occam.transition_function.IState;

public abstract class Transition implements ITransition {

	private final String name;
	private final IState activeState;
	private final IState nextState;
	
	public Transition(IState activeState, IState nextState) {
		name = ITransition.provideName();
		this.activeState = activeState;
		this.nextState = nextState;
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
		return activeState;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activeState == null) ? 0 : activeState.hashCode());
		result = prime * result + ((nextState == null) ? 0 : nextState.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Transition))
			return false;
		Transition other = (Transition) obj;
		if (activeState == null) {
			if (other.activeState != null)
				return false;
		} else if (!activeState.equals(other.activeState))
			return false;
		if (nextState == null) {
			if (other.nextState != null)
				return false;
		} else if (!nextState.equals(other.nextState))
			return false;
		return true;
	}

}
