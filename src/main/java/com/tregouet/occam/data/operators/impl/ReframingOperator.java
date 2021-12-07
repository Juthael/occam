package com.tregouet.occam.data.operators.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.operators.IConjunctiveOperator;
import com.tregouet.occam.data.operators.ILambdaExpression;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;
import com.tregouet.occam.transition_function.IState;

public class ReframingOperator extends Operator implements IOperator {
	
	IConjunctiveOperator rebuttedOperator;

	public ReframingOperator(IState activeState, List<IConjunctiveOperator> rebuttedOperators, IState nextState) {
		super(activeState, new ArrayList<IProduction>(), nextState);
		this.rebuttedOperator = rebuttedOperator;
	}
	
	@Override
	public boolean isBlank() {
		return false;
	}	
	
	@Override
	public List<ILambdaExpression> semantics() {
		List<ILambdaExpression> expressions = new ArrayList<>();
		ILambdaExpression negation = new Negation();
		for (ILambdaExpression expression : rebuttedOperator.semantics())
			negation.setArgument(null, expression);
		expressions.add(negation);
		return expressions;
	}	
	
	@Override 
	public String toString() {
		return "¬" + rebuttedOperator.getName();
	}	

}
