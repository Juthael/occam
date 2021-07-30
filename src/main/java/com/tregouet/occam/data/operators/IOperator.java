package com.tregouet.occam.data.operators;

import java.util.List;

import com.tregouet.occam.data.constructs.IConstruct;

public interface IOperator {
	
	IConstruct operateOn(IConstruct construct);
	
	List<ILambdaExpression> semantics();
	
	List<IProduction> operation();

}
