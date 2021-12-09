package com.tregouet.occam.data.transitions.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.transitions.IBasicOperator;
import com.tregouet.occam.data.transitions.IBasicProduction;
import com.tregouet.occam.data.transitions.ICompositeProduction;
import com.tregouet.occam.data.transitions.ILambdaExpression;
import com.tregouet.occam.data.transitions.IOperator;
import com.tregouet.occam.data.transitions.IProduction;
import com.tregouet.occam.transition_function.IState;

public class BasicOperator extends Transition implements IBasicOperator {

	private final Map<IIntentAttribute, IIntentAttribute> inputToOutput = new HashMap<>();
	private final List<IProduction> operation;
	private double informativity = -1.0;
	
	public BasicOperator(IState activeState, List<IProduction> operation, IState nextState) {
		super(activeState, nextState);
		this.operation = new ArrayList<>(operation);
		for (IProduction production : operation)
			inputToOutput.put(production.getSource(), production.getTarget());
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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((inputToOutput == null) ? 0 : inputToOutput.hashCode());
		result = prime * result + ((operation == null) ? 0 : operation.hashCode());
		return result;
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

}
