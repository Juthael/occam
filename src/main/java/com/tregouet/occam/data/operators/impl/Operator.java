package com.tregouet.occam.data.operators.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.operators.ILambdaExpression;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.transition_function.IState;

public class Operator implements IOperator {

	private static double binaryLogarithm(double arg) {
		return Math.log10(arg)/Math.log10(2);
	}
	private final IState activeState;
	private final Map<IIntentAttribute, IIntentAttribute> inputToOutput = new HashMap<>();
	private final List<IProduction> operation;
	private final IState nextState;
	
	private final double cost;

	public Operator(IState activeState, List<IProduction> operation, IState nextState) {
		this.activeState = activeState;
		this.operation = new ArrayList<>(operation);
		this.nextState = nextState;
		for (IProduction production : operation) {
			inputToOutput.put(production.getOperatorInput(), production.getOperatorOutput());
		}
		cost = calculateCost();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operator other = (Operator) obj;
		if (inputToOutput == null) {
			if (other.inputToOutput != null)
				return false;
		} else if (!inputToOutput.equals(other.inputToOutput))
			return false;
		if (nextState == null) {
			if (other.nextState != null)
				return false;
		} else if (!nextState.equals(other.nextState))
			return false;
		if (activeState == null) {
			if (other.activeState != null)
				return false;
		} else if (!activeState.equals(other.activeState))
			return false;
		return true;
	}

	@Override
	public double getCost() {
		return cost;
	}

	@Override
	public IState getNextState() {
		return nextState;
	}

	@Override
	public IState getOperatingState() {
		return activeState;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inputToOutput == null) ? 0 : inputToOutput.hashCode());
		result = prime * result + ((nextState == null) ? 0 : nextState.hashCode());
		result = prime * result + ((activeState == null) ? 0 : activeState.hashCode());
		return result;
	}
	
	@Override
	public IIntentAttribute operateOn(IIntentAttribute input) {
		return inputToOutput.get(input);
	}

	@Override
	public List<IProduction> operation() {
		return operation;
	}

	@Override
	public List<ILambdaExpression> semantics() {
		List<ILambdaExpression> expressions = new ArrayList<>();
		for (IProduction prod : operation)
			expressions.add(prod.semanticRule());
		return expressions;
	}

	@Override 
	public String toString() {
		StringBuilder sB = new StringBuilder();
		for (int i = 0 ; i < operation.size() ; i++) {
			sB.append(operation.get(i));
			if (i < operation.size() - 1)
				sB.append(", ");
		}
		return sB.toString();
	}
	
	private double calculateCost() {
		double currStateExtentSize = activeState.getExtent().size();
		double nextStateExtentSize = nextState.getExtent().size();
		return binaryLogarithm(currStateExtentSize / nextStateExtentSize);
	}

}
