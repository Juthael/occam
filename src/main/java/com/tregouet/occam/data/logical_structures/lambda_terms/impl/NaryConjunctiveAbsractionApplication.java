package com.tregouet.occam.data.logical_structures.lambda_terms.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.logical_structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.representations.transitions.productions.IProduction;

public class NaryConjunctiveAbsractionApplication extends ALambdaTerm implements ILambdaExpression {
	
	private List<AVariable> boundVariables = new ArrayList<>();
	private List<Set<NaryConjunctiveAbsractionApplication>> argSets = new ArrayList<>();
	
	
	public NaryConjunctiveAbsractionApplication(IConstruct construct) {
		super(construct);
	}

	@Override
	public boolean abstractAndApplyAccordingTo(IProduction production) {
		if (production.isEpsilon())
			return false;
		AVariable varToBind = production.getVariable();
		if (term.getVariables().contains(varToBind)) {
			int varIndex = boundVariables.indexOf(varToBind);
			if (varIndex == -1) {
				boundVariables.add(varToBind);
				Set<NaryConjunctiveAbsractionApplication> args = new HashSet<>();
				args.add(new NaryConjunctiveAbsractionApplication(production.getValue()));
				argSets.add(args);
			}
			else {
				argSets.get(varIndex).add(new NaryConjunctiveAbsractionApplication(production.getValue()));
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean isAnApplication() {
		return !boundVariables.isEmpty();
	}
	
	@Override
	public String toString() {
		return toString(false);
	}
	
	private String toString(boolean shorter) {
		if (boundVariables.isEmpty()) {
			if (term.asList().size() == 1)
				return term.toString();
			return "(" + term.toString() + ")";
		}
		StringBuilder sB = new StringBuilder();
		sB.append("(λ");
		for (AVariable var : boundVariables)
			sB.append(var.toString());
		sB.append(".").append(shorter ? term.getFunctionType() : term.toString()).append(")");
		for (Set<NaryConjunctiveAbsractionApplication> argSet : argSets) {
			sB.append("(");
			Iterator<NaryConjunctiveAbsractionApplication> argIte = argSet.iterator();
			while (argIte.hasNext()) {
				NaryConjunctiveAbsractionApplication nextArg = argIte.next();
				sB.append(shorter ? nextArg.toShorterString() : nextArg.toString());
				if (argIte.hasNext())
					sB.append(" Λ ");
			}
			sB.append(")");
		}
		return sB.toString();
	}

	@Override
	public String toShorterString() {
		return toString(true);
	}	

}
