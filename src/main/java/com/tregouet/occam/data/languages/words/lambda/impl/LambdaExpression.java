package com.tregouet.occam.data.languages.words.lambda.impl;

import java.util.List;

import com.tregouet.occam.data.languages.alphabets.domain_specific.IProduction;
import com.tregouet.occam.data.languages.alphabets.generic.AVariable;
import com.tregouet.occam.data.languages.words.construct.IConstruct;
import com.tregouet.occam.data.languages.words.lambda.ILambdaExpression;

public class LambdaExpression implements ILambdaExpression {

	private IConstruct term = null;
	private AVariable boundVariable = null;
	private LambdaExpression argument = null;
	
	public LambdaExpression(IConstruct term) {
		this.term = term;
	}
	
	public <P extends IProduction> LambdaExpression(List<P> productionList) {
		for (int i = 0 ; i < productionList.size() ; i++) {
			IProduction iProduction = productionList.get(i);
			if (term == null) {
				if (!iProduction.isEpsilon())
					term = iProduction.getValue();
			}
			else abstractAndApplyAccordingTo(iProduction);
		}
	}
	
	@Override
	public boolean abstractAndApplyAccordingTo(IProduction production) {
		if (production.isEpsilon())
			return false;
		if (argument == null) {
			AVariable varToBind = production.getVariable();
			if (term.getVariables().contains(varToBind)) {
				boundVariable = varToBind;
				argument = new LambdaExpression(production.getValue());
				return true;
			}
			return false;
		}
		return argument.abstractAndApplyAccordingTo(production);
	}
	
	@Override
	public String toString() {
		if (boundVariable == null) {
			if (term.asList().size() == 1)
				return term.toString();
			return "(" + term.toString() + ")";
		}
		StringBuilder sB = new StringBuilder();
		sB.append("(λ");
		sB.append(boundVariable.toString());
		sB.append(".");
		sB.append(term.toString());
		sB.append(")");
		if (argument.isAnApplication()) {
			sB.append("(");
			sB.append(argument.toString());
			sB.append(")");
		}
		else sB.append(argument.toString());
		return sB.toString();
	}

	@Override
	public boolean isAnApplication() {
		return boundVariable != null;
	}

}
