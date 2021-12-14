package com.tregouet.occam.data.abstract_machines.transitions.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transitions.IBasicOperator;
import com.tregouet.occam.data.abstract_machines.transitions.IBasicProduction;
import com.tregouet.occam.data.abstract_machines.transitions.ICompositeProduction;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.concepts.IIntentConstruct;
import com.tregouet.occam.data.languages.lambda.ILambdaExpression;

public class BasicOperator extends Transition implements IBasicOperator {

	private final Map<IIntentConstruct, IIntentConstruct> inputToOutput = new HashMap<>();
	private final List<IProduction> operation;
	
	public BasicOperator(IState activeState, List<IProduction> operation, IState nextState) {
		super(activeState, nextState);
		this.operation = new ArrayList<>(operation);
		for (IProduction production : operation)
			inputToOutput.put(production.getSource(), production.getTarget());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof BasicOperator))
			return false;
		BasicOperator other = (BasicOperator) obj;
		if (inputToOutput == null) {
			if (other.inputToOutput != null)
				return false;
		} else if (!inputToOutput.equals(other.inputToOutput))
			return false;
		if (operation == null) {
			if (other.operation != null)
				return false;
		} else if (!operation.equals(other.operation))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((inputToOutput == null) ? 0 : inputToOutput.hashCode());
		result = prime * result + ((operation == null) ? 0 : operation.hashCode());
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
	public IIntentConstruct operateOn(IIntentConstruct input) {
		return inputToOutput.get(input);
	}

	@Override
	public List<IProduction> operation() {
		return operation;
	}

	@Override
	public List<ILambdaExpression> semantics() {
		List<ILambdaExpression> expressions = new ArrayList<>();
		for (IProduction prod : operation) {
			if (prod instanceof ICompositeProduction) {
				ICompositeProduction composite = (ICompositeProduction) prod;
				for (IBasicProduction basicProd : composite.getComponents())
					expressions.add(basicProd.semanticRule());
			}
			else {
				IBasicProduction basic = (IBasicProduction) prod;
				expressions.add(basic.semanticRule());
			}
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

	@Override
	public boolean isReframer() {
		return false;
	}

}
