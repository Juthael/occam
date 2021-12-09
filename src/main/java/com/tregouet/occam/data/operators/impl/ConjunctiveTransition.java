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
	private List<IBasicOperator> operators = new ArrayList<>();
	private IReframer reframer;
	private final IState operatingState;
	private final IState nextState;

	public ConjunctiveTransition(ITransition transition) {
		name = IConjunctiveTransition.provideName();
		if (transition instanceof IReframer)
			reframer = (IReframer) transition;
		else operators.add((IBasicOperator) transition);
		operatingState = transition.getOperatingState();
		nextState = transition.getNextState();
	}

	@Override
	public boolean addTransition(ITransition transition) {
		if (this.operatingState.equals(transition.getOperatingState()) 
				&& this.nextState.equals(transition.getNextState())) {
			if (transition instanceof IReframer)
				reframer = (IReframer) transition;
			else operators.add((IBasicOperator) transition);
			return true;
		}
		return false;
	}

	@Override
	public List<ITransition> getComponents() {
		List<ITransition> components = new ArrayList<>(operators);
		components.add(reframer);
		return components;
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
		for (ITransition transition : getComponents()) {
			if (!transition.isBlank())
				return false;
		}
		return true;
	}

	@Override
	public IIntentAttribute operateOn(IIntentAttribute input) {
		IIntentAttribute output = null;
		int opIdx = 0;
		while (output == null && opIdx < operators.size())
			output = operators.get(opIdx++).operateOn(input);
		return output;
	}

	@Override
	public List<IProduction> operation() {
		List<IProduction> operations = new ArrayList<>();
		for (IBasicOperator operator : operators) {
			operations.addAll(operator.operation());
		}
		return operations;
	}

	@Override
	public List<ILambdaExpression> semantics() {
		List<ILambdaExpression> semantics = new ArrayList<>();
		for (IBasicOperator operator : operators)
			semantics.addAll(operator.semantics());
		return semantics;
	}

	@Override
	public List<IBasicOperator> getOperators() {
		return operators;
	}

	@Override
	public IReframer getReframer() {
		return reframer;
	}

}
