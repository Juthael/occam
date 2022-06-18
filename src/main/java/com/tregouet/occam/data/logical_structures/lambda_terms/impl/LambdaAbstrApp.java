package com.tregouet.occam.data.logical_structures.lambda_terms.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.logical_structures.lambda_terms.IBindings;
import com.tregouet.occam.data.logical_structures.lambda_terms.ILambdaAbstrApp;
import com.tregouet.occam.data.logical_structures.lambda_terms.ILambdaExpression;
import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.properties.computations.abstr_app.IAbstractionApplication;
import com.tregouet.occam.data.problem_space.states.productions.IBasicProduction;

public class LambdaAbstrApp implements ILambdaAbstrApp {
	
	private ILambdaExpression term = null;
	private IBindings bindings = null;
	private List<IConstruct> arguments = null;
	
	public LambdaAbstrApp(IConstruct construct, IAbstractionApplication abstrApp) {
		term = construct;
		arguments = new ArrayList<>();
		List<AVariable> boundVars = new ArrayList<>();
		for (IBasicProduction prod : abstrApp.getArguments()) {
			if (!prod.isEpsilon() && !prod.isIdentityProd()) {
				boundVars.add(prod.getVariable());
				arguments.add(prod.getValue());
			}
		}
		bindings = new Bindings(boundVars);		
	}
	
	private LambdaAbstrApp(ILambdaExpression term, IBindings bindings, List<IConstruct> arguments) {
		this.term = term;
		this.bindings = bindings;
		this.arguments = arguments;
	}	

	@Override
	public ILambdaExpression abstractAndApply(IAbstractionApplication abstrApp, boolean safeMode) {
		if (safeMode) {
			boolean capturableVars = checkThatVariablesToBindAreFree(abstrApp.getBindings());
			if (!capturableVars)
				return null;
		}
		List<AVariable> boundVars = new ArrayList<>();
		List<IConstruct> arguments = new ArrayList<>();
		for (IBasicProduction prod : abstrApp.getArguments()) {
			if (!prod.isEpsilon() && !prod.isIdentityProd()) {
				boundVars.add(prod.getVariable());
				arguments.add(prod.getValue());
			}
		}
		if (!boundVars.isEmpty())
			return new LambdaAbstrApp(this, new Bindings(boundVars), arguments);
		else return this;
	}

	@Override
	public Set<AVariable> getFreeVariables() {
		Set<AVariable> freeVariables = new HashSet<>(term.getFreeVariables());
		freeVariables.removeAll(bindings.getBoundVariables());
		for (IConstruct argument : arguments)
			freeVariables.addAll(argument.getVariables());
		return freeVariables;
	}

	@Override
	public boolean isAbstractionApplication() {
		return true;
	}
		
	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder();
		sB.append("(λ" + bindings.toString() + ".")
			.append((term.isAbstractionApplication() ? "(" + term.toString() + "))" : term.toString() + ")"));
		for (IConstruct argument : arguments) {
			sB.append(argument.size() == 1 ? " " + argument.toString() : " (" + argument.toString() + ")");
		}
		return sB.toString();
	}
	
	private boolean checkThatVariablesToBindAreFree(IBindings bindings) {
		return getFreeVariables().containsAll(bindings.getBoundVariables());
	}

}
