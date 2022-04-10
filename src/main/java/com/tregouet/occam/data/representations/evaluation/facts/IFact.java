package com.tregouet.occam.data.representations.evaluation.facts;

import com.tregouet.occam.data.logical_structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.logical_structures.languages.words.IWord;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;

public interface IFact extends IWord<IContextualizedProduction> {
	
	ILambdaExpression asLambda();

}
