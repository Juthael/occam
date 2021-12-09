package com.tregouet.occam.data.abstract_machines.transitions;

import java.util.List;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.languages.lambda.ILambdaExpression;

public interface IOperator extends ITransition {
	
	IIntentAttribute operateOn(IIntentAttribute input);
	
	List<IProduction> operation();	
	
	List<ILambdaExpression> semantics();
	
	@Override
	String toString();	

}
