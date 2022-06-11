package com.tregouet.occam.data.logical_structures.lambda_terms.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.logical_structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.logical_structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.applications.IApplication;
import com.tregouet.occam.data.problem_space.states.productions.IProduction;

public class LambdaExpression extends ALambdaTerm implements ILambdaExpression {

	private IBindings bindings = null;
	private List<LambdaExpression> arguments = null;

	public LambdaExpression(IConstruct term) {
		super(term);
	}

	public LambdaExpression(List<IApplication> applicationList) {
		super(null);
		for (IApplication application : applicationList) {
			if (term == null) {
				if (!application.isEpsilon())
					term = application.getValue().copy();
			} else
				abstractAndApply(application);
		}
		if (term == null)
			term = new Construct(new String[] {"ε"});
	}

	@Override
	public boolean abstractAndApply(IApplication application) {
		if (application.isEpsilon())
			return false;
		if (bindings == null) {
			IBindings bindings = application.getBindings();
			if (canBeAbstractedWithSpecifiedBindings(term, bindings)) {
				this.bindings = bindings;
				arguments = new ArrayList<>();
				for (IProduction production : application.getArguments())
					arguments.add(new LambdaExpression(production.getValue()));
				return true;
			}
			return false;
		}
		boolean abstAppProceeded =  false;
		for (LambdaExpression argument : arguments) {
			if (argument.abstractAndApply(application))
				abstAppProceeded = true;
		}
		return abstAppProceeded;
	}

	@Override
	public boolean isAnApplication() {
		return bindings != null;
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
		if (bindings == null) {
			if (term.asList().size() == 1) {
				return (term.isAbstract() ? "" : term.toString());
			}
			return "(" + term.toString() + ")";
		}
		StringBuilder sB = new StringBuilder();
		if (term.asList().size() > 1) { //otherwise function is identity function
			sB.append("(λ");
			sB.append(bindings.toString());
			sB.append(".");
			sB.append(shorter ? getFunctionType(term) : term.toString());
			sB.append(")");
		}
		for (ILambdaExpression argument : arguments) {
			if (argument.isAnApplication()) {
				sB.append(" (");
				sB.append(argument.toString());
				sB.append(")");
			} else
				sB.append(" " + argument.toString());
		}
		return sB.toString();	
	}
	
	private static boolean canBeAbstractedWithSpecifiedBindings(IConstruct term, IBindings bindings) {
		List<AVariable> varToBind = bindings.getBoundVariables();
		int varIdx = 0;
		AVariable requested = varToBind.get(varIdx);
		for (ISymbol symbol : term.asList()) {
			if (symbol.equals(requested)) {
				varIdx++;
				if (varIdx == varToBind.size())
					return true;
				else requested = varToBind.get(varIdx);
			}
		}
		return false;
	}
	
	private static String getFunctionType(IConstruct construct) {
		List<AVariable> vars = construct.getVariables();
		if (vars.isEmpty())
			return null;
		StringBuilder sB = new StringBuilder();
		sB.append("τ_(");
		Iterator<AVariable> varIte = vars.iterator();
		while (varIte.hasNext()) {
			sB.append(varIte.next().toString());
			if (varIte.hasNext())
				sB.append(", ");
		}
		sB.append(")");
		return sB.toString();
	}	

}
