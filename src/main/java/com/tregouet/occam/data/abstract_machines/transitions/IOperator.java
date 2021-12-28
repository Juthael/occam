package com.tregouet.occam.data.abstract_machines.transitions;

import java.util.List;

import com.tregouet.occam.alg.calculators.costs.ICosted;
import com.tregouet.occam.data.concepts.IIntentConstruct;
import com.tregouet.occam.data.languages.lambda.ILambdaExpression;

public interface IOperator extends ITransition {
	
	IIntentConstruct operateOn(IIntentConstruct input);
	
	List<IProduction> operation();	
	
	List<ILambdaExpression> semantics();
	
	@Override
	String toString();	

}
