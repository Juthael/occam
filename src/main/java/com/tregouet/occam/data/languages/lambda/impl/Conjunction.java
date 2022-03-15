package com.tregouet.occam.data.languages.lambda.impl;

import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.languages.lambda.ILambdaExpression;

public class Conjunction implements ILambdaExpression {

	private final List<ILambdaExpression> arguments;
	
	public Conjunction(List<ILambdaExpression> arguments) {
		this.arguments = arguments;
	}
	
	@Override
	public boolean appliesAFunction() {
		boolean function = false;
		Iterator<ILambdaExpression> argIte = arguments.iterator();
		while (function == false && argIte.hasNext())
			function = argIte.next().appliesAFunction();
		return function;
	}

	@Override
	public boolean binds(AVariable boundVar) {
		boolean doBind = false;
		Iterator<ILambdaExpression> argIte = arguments.iterator();
		while (doBind == false && argIte.hasNext())
			doBind = argIte.next().binds(boundVar);
		return doBind;
	}

	@Override
	public boolean setArgument(AVariable boundVar, ILambdaExpression argument) {
		// No argument for conjunctions, since conjunctive productions must be the final symbol of a property
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		Iterator<ILambdaExpression> argIte = arguments.iterator();
		while (argIte.hasNext()) {
			sB.append(argIte.next().toString());
			if (argIte.hasNext())
				sB.append(" || ");
		}
		return sB.toString();
	}

}
