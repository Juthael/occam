package com.tregouet.occam.data.operators;

import java.util.List;

import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.transition_function.IState;

public interface IOperator {
	
	IIntentAttribute operateOn(IIntentAttribute input);
	
	List<ILambdaExpression> semantics();
	
	List<IProduction> operation();
	
	IState getOperatingState();
	
	IState getNextState();
	
	double getCost();

}
