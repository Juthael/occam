package com.tregouet.occam.data.abstract_machines.transitions;

import java.util.List;

import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.languages.lambda.ILambdaExpression;

public interface IOperator extends ITransition {
	
	IDenotation operateOn(IDenotation input);
	
	List<IProduction> operation();	
	
	List<ILambdaExpression> semantics();
	
	@Override
	String toString();	

}
