package com.tregouet.occam.data.operators;

import java.util.List;

import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.transition_function.IState;

public interface IOperator {
	
	public int hashCode();
	
	boolean equals(Object o);
	
	double getCost();
	
	IState getNextState();
	
	IState getOperatingState();
	
	IIntentAttribute operateOn(IIntentAttribute input);
	
	List<IProduction> operation();
	
	List<ILambdaExpression> semantics();

}
