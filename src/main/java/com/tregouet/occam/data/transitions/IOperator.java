package com.tregouet.occam.data.transitions;

import java.util.List;
import com.tregouet.occam.data.concepts.IIntentAttribute;

public interface IOperator extends ITransition {
	
	IIntentAttribute operateOn(IIntentAttribute input);
	
	List<IProduction> operation();	
	
	List<ILambdaExpression> semantics();
	
	@Override
	String toString();	

}
