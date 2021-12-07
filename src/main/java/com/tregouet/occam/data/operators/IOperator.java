package com.tregouet.occam.data.operators;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.transition_function.IState;

public interface IOperator extends ITransition {
	
	IIntentAttribute operateOn(IIntentAttribute input);
	
	List<IProduction> operation();	
	
	List<ILambdaExpression> semantics();
	
	@Override
	String toString();	

}
