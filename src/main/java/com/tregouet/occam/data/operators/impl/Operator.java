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

	private final IState operatingState;
	private final Map<IIntentAttribute, IIntentAttribute> inputToOutput = new HashMap<>();
	private final List<IProduction> operation;
	private final IState nextState;
	private final double cost;
	
	public Operator(IState operatingState, List<IProduction> operation, IState nextState) {
		this.operatingState = operatingState;
		this.operation = new ArrayList<>(operation);
		this.nextState = nextState;
		for (IProduction production : operation) {
			if (!inputToOutput.containsKey(production.getOperatorInput()))
				inputToOutput.put(production.getOperatorInput(), production.getOperatorOutput());
		}
		cost = calculateCost();
	}

	@Override
	public IIntentAttribute operateOn(IIntentAttribute input) {
		return inputToOutput.get(input);
	}

	@Override
	public List<ILambdaExpression> semantics() {
		List<ILambdaExpression> expressions = new ArrayList<>();
		for (IProduction prod : operation)
			expressions.add(prod.semanticRule());
		return expressions;
	}

	@Override
	public List<IProduction> operation() {
		return operation;
	}

	@Override
	public IState getOperatingState() {
		return operatingState;
	}

	@Override
	public IState getNextState() {
		return nextState;
	}
	
	private double calculateCost() {
		double currStateExtentSize = operatingState.getExtent().size();
		double nextStateExtentSize = nextState.getExtent().size();
		return binaryLogarithm(currStateExtentSize / nextStateExtentSize);
	}
	
	private static double binaryLogarithm(double arg) {
		return Math.log10(arg)/Math.log10(2);
	}

	@Override
	public double getCost() {
		return cost;
	}

}
