package com.tregouet.occam.data.languages.specific.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.lambda.ILambdaExpression;
import com.tregouet.occam.data.languages.lambda.impl.Conjunction;
import com.tregouet.occam.data.languages.lambda.impl.LambdaExpression;
import com.tregouet.occam.data.languages.specific.IBasicProduction;
import com.tregouet.occam.data.languages.specific.ICompositeProduction;
import com.tregouet.occam.data.languages.specific.IProduction;

public class CompositeProduction extends Production implements ICompositeProduction {

	private final List<IConstruct> conjunctiveValues;
	
	public CompositeProduction(AVariable variable, List<IConstruct> conjunctiveValues) {
		super(variable);
		this.conjunctiveValues = conjunctiveValues;
	}
	
	@Override
	public ILambdaExpression semanticRule() {
		List<ILambdaExpression> arguments = new ArrayList<>();
		for (IConstruct value : conjunctiveValues) {
			arguments.add(new LambdaExpression(value));
		}
		return new Conjunction(arguments);
	}

	@Override
	public ILambdaExpression asLambda(List<IProduction> nextProductions) {
		//CAUTION : a composite production must be the last element of a property, so the parameter must be empty
		return asLambda();
	}

	@Override
	public ICompositeProduction combine(IBasicProduction component) {
		if (this.variable.equals(component.getVariable())){
			conjunctiveValues.add(component.getValue());
			return this;
		}
		return null;
	}

	@Override
	public List<IConstruct> getValues() {
		return conjunctiveValues;
	}

	@Override
	public ILambdaExpression asLambda() {
		return semanticRule();
	}

}
