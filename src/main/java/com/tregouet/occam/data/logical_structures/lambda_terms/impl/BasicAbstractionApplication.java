package com.tregouet.occam.data.logical_structures.lambda_terms.impl;

import java.util.List;

import com.tregouet.occam.data.logical_structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.representations.transitions.productions.IProduction;

public class BasicAbstractionApplication extends ALambdaTerm implements ILambdaExpression {

	private AVariable boundVariable = null;
	private BasicAbstractionApplication argument = null;

	public BasicAbstractionApplication(IConstruct term) {
		super(term);
	}

	public <P extends IProduction> BasicAbstractionApplication(List<P> productionList) {
		super(null);
		for (P iProduction : productionList) {
			if (term == null) {
				if (!iProduction.isEpsilon())
					term = iProduction.getValue();
			} else
				abstractAndApplyAccordingTo(iProduction);
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
				argument = new BasicAbstractionApplication(production.getValue());
				return true;
			}
			return false;
		}
		return argument.abstractAndApplyAccordingTo(production);
	}

	@Override
	public boolean isAnApplication() {
		return boundVariable != null;
	}

	@Override
	public String toShorterString() {
		return toString(true);
	}

	@Override
	public String toString() {
		return toString(false);
	}

	private String toString(boolean shorter) {
		if (boundVariable == null) {
			if (term.asList().size() == 1)
				return term.toString();
			return "(" + term.toString() + ")";
		}
		StringBuilder sB = new StringBuilder();
		sB.append("(λ");
		sB.append(boundVariable.toString());
		sB.append(".");
		sB.append(shorter ? term.getFunctionType() : term.toString());
		sB.append(")");
		if (argument.isAnApplication()) {
			sB.append("(");
			sB.append(argument.toString());
			sB.append(")");
		} else
			sB.append(argument.toString());
		return sB.toString();
	}

}
