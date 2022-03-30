package com.tregouet.occam.data.languages.words.fact;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.languages.words.IWord;
import com.tregouet.occam.data.languages.words.lambda.ILambdaExpression;

public interface IFact extends IWord<IContextualizedProduction> {
	
	ILambdaExpression asLambda();

}
