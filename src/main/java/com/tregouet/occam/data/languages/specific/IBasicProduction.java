package com.tregouet.occam.data.languages.specific;

import java.util.List;

import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.lambda.ILambdaExpression;

public interface IBasicProduction extends IProduction {
	
	ILambdaExpression asLambda(List<IProduction> nextProductions);
	
	ILambdaExpression asLambdaFromBasicProd(List<IBasicProduction> nextProductions);
	
	IConstruct getValue();
	
	AVariable getVariable();
	
	ILambdaExpression semanticRule();

}
