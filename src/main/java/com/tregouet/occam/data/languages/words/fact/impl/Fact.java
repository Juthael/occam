package com.tregouet.occam.data.languages.words.fact.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.languages.words.fact.IFact;
import com.tregouet.occam.data.languages.words.lambda.ILambdaExpression;
import com.tregouet.occam.data.languages.words.lambda.impl.LambdaExpression;

public class Fact implements IFact {

	private final List<IContextualizedProduction> productionList;
	
	public Fact(List<IContextualizedProduction> productionList) {
		this.productionList = productionList;
	}
	
	@Override
	public List<IContextualizedProduction> asList() {
		return new ArrayList<>(productionList);
	}

	@Override
	public IFact copy() {
		return new Fact(asList());
	}

	@Override
	public ILambdaExpression asLambda() {
		return new LambdaExpression(productionList);
	}

}
