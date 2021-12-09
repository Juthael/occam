package com.tregouet.occam.data.transitions;

import java.util.List;

import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.IConstruct;

public interface IBasicProduction extends IProduction {
	
	ILambdaExpression asLambda(List<IProduction> nextProductions);
	
	ILambdaExpression asLambdaFromBasicProd(List<IBasicProduction> nextProductions);
	
	IConstruct getValue();
	
	AVariable getVariable();
	
	ILambdaExpression semanticRule();

}
