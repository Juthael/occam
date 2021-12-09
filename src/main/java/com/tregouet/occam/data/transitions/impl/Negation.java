package com.tregouet.occam.data.transitions.impl;

import java.util.List;

import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.transitions.ILambdaExpression;

public class Negation implements ILambdaExpression {
	
	List<ILambdaExpression> terms;

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
		terms.add(argument);
		return true;
	}
	
	@Override
	public String toString() {
		String nL = System.lineSeparator();
		StringBuilder sB = new StringBuilder();
		char conjunction = '∧';
		sB.append("¬(");
		int nbOfTerms = terms.size();
		for (int i = 0 ; i < nbOfTerms ; i++) {
			sB.append(terms.get(i).toString());
			if (i < nbOfTerms - 1) {
				sB.append(nL + conjunction);
			}
			sB.append(")");
		}
		return sB.toString();
	}	

}
