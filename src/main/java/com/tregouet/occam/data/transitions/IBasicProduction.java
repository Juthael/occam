package com.tregouet.occam.data.transitions;

import java.util.List;

import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.lambdas.ILambdaExpression;

public interface IBasicProduction extends IProduction {
	
	ILambdaExpression asLambda(List<IProduction> nextProductions);
	
	ILambdaExpression asLambdaFromBasicProd(List<IBasicProduction> nextProductions);
	
	IConstruct getValue();
	
	AVariable getVariable();
	
	ILambdaExpression semanticRule();

}
