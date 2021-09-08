package com.tregouet.occam.data.operators.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.operators.IConjunctiveOperator;
import com.tregouet.occam.data.operators.ILambdaExpression;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.transition_function.IInfoMeter;
import com.tregouet.occam.transition_function.IState;

public class ConjunctiveOperator implements IConjunctiveOperator {
	
	private String name;
	private List<IOperator> operators = new ArrayList<>();
	private double informativity;
	private final IState operatingState;
	private final IState nextState;

	public ConjunctiveOperator(IOperator operator) {
		name = operator.getName();
		operators.add(operator);
		informativity = operator.getInformativity();
		operatingState = operator.getOperatingState();
		nextState = operator.getNextState();
	}

	@Override
	public double getInformativity() {
		return informativity;
	}

	@Override
	public void setInformativity(IInfoMeter infometer) {
		// irrelevant
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
		return false;
	}

	@Override
	public IIntentAttribute operateOn(IIntentAttribute input) {
		IIntentAttribute output = null;
		int opIdx = 0;
		while (output == null && opIdx < operators.size()) {
			output = operators.get(opIdx).operateOn(input);
			opIdx++;
		}
		return output;
	}

	@Override
	public List<IProduction> operation() {
		List<IProduction> operations = new ArrayList<>();
		for (IOperator operator : operators)
			operations.addAll(operator.operation());
		return operations;
	}

	@Override
	public List<ILambdaExpression> semantics() {
		List<ILambdaExpression> semantics = new ArrayList<>();
		for (IOperator operator : operators)
			semantics.addAll(operator.semantics());
		return semantics;
	}

	@Override
	public boolean addOperator(IOperator operator) {
		if (this.operatingState.equals(operator.getOperatingState()) 
				&& this.nextState.equals(operator.getNextState())) {
			operators.add(operator);
			return true;
		}
		return false;
	}

}
