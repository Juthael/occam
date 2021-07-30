package com.tregouet.occam.data.operators.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.ISymbol;
import com.tregouet.occam.data.constructs.impl.Construct;
import com.tregouet.occam.data.operators.ILambdaExpression;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;

public class Production implements IProduction {

	AVariable variable;
	IConstruct value;
	IIntentAttribute operatesOn;
	IIntentAttribute yields;
	IOperator operator;
	
	public Production(AVariable variable, IConstruct value, IIntentAttribute operatesOn, IIntentAttribute yields) {
		this.variable = variable;
		this.value = value;
		this.operatesOn = operatesOn;
		this.yields = yields;
	}

	@Override
	public IConstruct doAbstract(IConstruct construct) {
		List<ISymbol> valueList = value.getListOfSymbols();
		List<ISymbol> returned = new ArrayList<>();
		List<ISymbol> buffer = new ArrayList<>();
		Iterator<ISymbol> constructIte = construct.getListOfSymbols().iterator(); 
		int valueIdx = 0;
		while (constructIte.hasNext()) {
			ISymbol nextSymbol = constructIte.next();
			if (nextSymbol.equals(valueList.get(valueIdx))) {
				if (valueIdx == valueList.size() - 1) {
					returned.add(variable);
					constructIte.forEachRemaining(s -> returned.add(s));
				}
				else {
					buffer.add(nextSymbol);
					valueIdx++;
				}
			}
			else {
				if (!buffer.isEmpty()) {
					returned.addAll(buffer);
					buffer.clear();
					valueIdx = 0;
				}
				returned.add(nextSymbol);
			}
		}
		return new Construct(returned);
	}

	@Override
	public AVariable getVariable() {
		return variable;
	}

	@Override
	public IConstruct getValue() {
		return value;
	}

	@Override
	public boolean derives(AVariable var) {
		return var.equals(variable);
	}

	@Override
	public IConstruct derive(IConstruct construct) {
		List<ISymbol> returned = new ArrayList<>();
		for (ISymbol symbol : construct.getListOfSymbols()) {
			if (symbol.equals(variable))
				returned.addAll(value.getListOfSymbols());
			else returned.add(symbol);
		}
		return new Construct(returned);
	}

	@Override
	public ILambdaExpression semanticRule() {
		return new LambdaExpression(value);
	}

	@Override
	public ILambdaExpression asLambda(List<IProduction> nextProductions) {
		ILambdaExpression lambda = semanticRule();
		if (value.isAbstract()) {
			int nbOfRemainingValueVars = value.getVariables().size();
			List<IProduction> remainingProd = new ArrayList<>(nextProductions);
			Iterator<IProduction> prodIte = nextProductions.iterator();
			while (nbOfRemainingValueVars > 0 && prodIte.hasNext()) {
				IProduction nextProd = prodIte.next();
				AVariable prodVar = nextProd.getVariable();
				if (value.getVariables().contains(prodVar)) {
					remainingProd.remove(nextProd);
					lambda.setArgument(prodVar, nextProd.asLambda(remainingProd));
					nbOfRemainingValueVars--;
				}
			}
		}
		return lambda;
	}

	@Override
	public void setOperator(IOperator operator) {
		this.operator = operator;

	}

	@Override
	public ICategory getGenus() {
		return yields.getCategory();
	}

	@Override
	public ICategory getInstance() {
		return operatesOn.getCategory();
	}

}
