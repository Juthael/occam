package com.tregouet.occam.data.abstract_machines.transitions.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.abstract_machines.transitions.IBasicOperator;
import com.tregouet.occam.data.abstract_machines.transitions.IConjunctiveTransition;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.abstract_machines.transitions.IReframer;
import com.tregouet.occam.data.abstract_machines.transitions.ITransition;
import com.tregouet.occam.data.concepts.IIntentConstruct;
import com.tregouet.occam.data.languages.lambda.ILambdaExpression;

public class ConjunctiveTransition implements IConjunctiveTransition {
	
	private String name;
	private List<IBasicOperator> operators = new ArrayList<>();
	private IReframer reframer = null;
	private final IState operatingState;
	private final IState nextState;

	public ConjunctiveTransition(ITransition transition) {
		name = IConjunctiveTransition.provideName();
		if (transition.isReframer())
			reframer = (IReframer) transition;
		else operators.add((IBasicOperator) transition);
		operatingState = transition.getOperatingState();
		nextState = transition.getNextState();
	}

	@Override
	public boolean addTransition(ITransition transition) {
		if (this.operatingState.equals(transition.getOperatingState()) 
				&& this.nextState.equals(transition.getNextState())) {
			if (transition.isReframer())
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
	public List<IBasicOperator> getOperators() {
		return operators;
	}

	@Override
	public IReframer getReframer() {
		return reframer;
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
	public IIntentConstruct operateOn(IIntentConstruct input) {
		IIntentConstruct output = null;
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
	public boolean isReframer() {
		return reframer != null;
	}

}
