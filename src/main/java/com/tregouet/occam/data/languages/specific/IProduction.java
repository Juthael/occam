package com.tregouet.occam.data.languages.specific;

import java.util.List;

import com.tregouet.occam.data.languages.ISymbol;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.lambda.ILambdaExpression;

public interface IProduction extends ISymbol {
	
	AVariable getVariable();
	
	boolean derives(AVariable var);
	
	@Override
	boolean equals(Object o);
	
	@Override
	int hashCode();
	
	@Override
	String toString();
	
	ILambdaExpression semanticRule();
	
	ILambdaExpression asLambda(List<IProduction> nextProductions);
	
	ICompositeProduction combine(IBasicProduction component);

}
