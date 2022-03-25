package com.tregouet.occam.data.languages.alphabets.domain_specific;

import java.util.List;

import com.tregouet.occam.data.languages.alphabets.ISymbol;
import com.tregouet.occam.data.languages.alphabets.generic.AVariable;
import com.tregouet.occam.data.languages.words.construct.IConstruct;
import com.tregouet.occam.data.languages.words.lambda.ILambdaExpression;

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
	
	//Denotes an empty string of symbols. A symbols are productions, also denotes that no variable is derived. 
	boolean isEpsilon();
	
	IConstruct getValue();

}
