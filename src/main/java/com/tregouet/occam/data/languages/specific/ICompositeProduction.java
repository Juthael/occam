package com.tregouet.occam.data.languages.specific;

import java.util.List;

import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.lambda.ILambdaExpression;

public interface ICompositeProduction extends IProduction {
	
	List<IConstruct> getValues();
	
	ILambdaExpression asLambda();

}
