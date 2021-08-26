package com.tregouet.occam.data.operators.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.operators.IBasicProduction;
import com.tregouet.occam.data.operators.ICompositeProduction;
import com.tregouet.occam.data.operators.ILambdaExpression;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.transition_function.IState;

public class Operator implements IOperator {

	private final String name;
	private final IState activeState;
	private final Map<IIntentAttribute, IIntentAttribute> inputToOutput = new HashMap<>();
	private final List<IProduction> operation;
	private final IState nextState;
	private final double cost;
	
	public Operator(IState activeState, List<IProduction> operation, IState nextState) {
		name = IOperator.provideName();
		this.activeState = activeState;
		this.operation = new ArrayList<>(operation);
		this.nextState = nextState;
		for (IProduction production : operation) {
			inputToOutput.put(production.getSource(), production.getTarget());
			production.setOperator(this);
		}
		cost = calculateCost();
	}

	private static double binaryLogarithm(double arg) {
		return Math.log10(arg)/Math.log10(2);
	}

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
	public boolean isBlank() {
		boolean isBlank = true;
		int prodIdx = 0;
		while (isBlank && prodIdx < operation.size()) {
			isBlank = operation.get(prodIdx).isBlank();
			prodIdx++;
		}
		return isBlank;
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
			if (prod instanceof ICompositeProduction) {
				ICompositeProduction composite = (ICompositeProduction) prod;
				for (IBasicProduction basicProd : composite.getComponents())
					expressions.add(basicProd.semanticRule());
			}
			else {
				IBasicProduction basic = (IBasicProduction) prod;
				expressions.add(basic.semanticRule());
			}
		return expressions;
	}
	

	@Override 
	public String toString() {
		if (this.isBlank())
			return new String();
		StringBuilder sB = new StringBuilder();
		for (IProduction production : operation) {
			if (!production.isBlank())
				sB.append(production.toString() + System.lineSeparator());
		}
		sB.deleteCharAt(sB.length() - 1);
		return sB.toString();
	}

	private double calculateCost() {
		if (this.isBlank())
			return 0.0;
		double currStateExtentSize = activeState.getExtentSize();
		double nextStateExtentSize = nextState.getExtentSize();
		return - binaryLogarithm(currStateExtentSize / nextStateExtentSize);
	}
	
	

}
