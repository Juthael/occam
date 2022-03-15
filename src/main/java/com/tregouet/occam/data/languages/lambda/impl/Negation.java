package com.tregouet.occam.data.languages.lambda.impl;

import java.util.List;

import com.tregouet.occam.data.alphabets.generic.AVariable;
import com.tregouet.occam.data.languages.lambda.ILambdaExpression;

public class Negation implements ILambdaExpression {
	
	List<ILambdaExpression> arguments;

	public Negation() {
	}

	@Override
	public boolean appliesAFunction() {
		return true;
	}

	@Override
	public boolean binds(AVariable boundVar) {
		return false;
	}

	@Override
	public boolean setArgument(AVariable boundVar, ILambdaExpression argument) {
		arguments.add(argument);
		return true;
	}
	
	@Override
	public String toString() {
		String nL = System.lineSeparator();
		StringBuilder sB = new StringBuilder();
		char conjunction = '∧';
		sB.append("¬(");
		int nbOfTerms = arguments.size();
		for (int i = 0 ; i < nbOfTerms ; i++) {
			sB.append(arguments.get(i).toString());
			if (i < nbOfTerms - 1) {
				sB.append(nL + conjunction);
			}
			sB.append(")");
		}
		return sB.toString();
	}	

}
