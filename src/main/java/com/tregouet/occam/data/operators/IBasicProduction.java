package com.tregouet.occam.data.operators;

import java.util.List;

import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.IConstruct;

public interface IBasicProduction extends IProduction {
	
	ILambdaExpression semanticRule();
	
	ILambdaExpression asLambda(List<IProduction> nextProductions);
	
	ILambdaExpression asLambdaFromBasicProd(List<IBasicProduction> nextProductions);
	
	AVariable getVariable();
	
	IConstruct getValue();

}
