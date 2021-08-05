package com.tregouet.occam.data.operators.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.ISymbol;
import com.tregouet.occam.data.constructs.impl.Construct;
import com.tregouet.occam.data.operators.IBasicProduction;
import com.tregouet.occam.data.operators.ICompositeProduction;
import com.tregouet.occam.data.operators.ILambdaExpression;
import com.tregouet.occam.data.operators.IProduction;

public class BasicProduction extends Production implements IBasicProduction {

	private static final long serialVersionUID = 1701074226278101143L;
	private final AVariable variable;
	private final IConstruct value;
	
	public BasicProduction(AVariable variable, IConstruct value, IIntentAttribute operatorInput, IIntentAttribute operatorOutput) {
		super(operatorInput, operatorOutput);
		this.variable = variable;
		this.value = value;
	}
	
	protected BasicProduction(IIntentAttribute operatorInput, IIntentAttribute operatorOutput) {
		super(operatorInput, operatorOutput);
		variable = null;
		value = null;
	}

	/**
	 * Given as an example to show what a Production is meant to be. No real utility. 
	 * @param construct
	 * @return
	 */
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
	public boolean derives(AVariable var) {
		return var.equals(variable);
	}

	/**
	 * Given as an example to show what a Production is meant to be. No real utility. 
	 * @param construct
	 * @return
	 */
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

	public IConstruct getValue() {
		return value;
	}
	
	public AVariable getVariable() {
		return variable;
	}
	
	public ILambdaExpression semanticRule() {
		return new LambdaExpression(value);
	}
	
	@Override
	public String toString() {
		return "[" + variable.toString() + " ::= " + value.toString() + "]";  
	}

	@Override
	public String getLabel() {
		return toString();
	}

	@Override
	public List<IConstruct> getValues() {
		return new ArrayList<>(Arrays.asList(new IConstruct[] {value}));
	}

	@Override
	public List<AVariable> getVariables() {
		return new ArrayList<>(Arrays.asList(new AVariable[] {variable}));
	}
	
	@Override
	public ILambdaExpression asLambda(List<IProduction> nextProductions) {
		List<IBasicProduction> basicProds = new ArrayList<>();
		for (IProduction prod : nextProductions) {
			if (prod instanceof ICompositeProduction)
				basicProds.addAll(((ICompositeProduction) prod).getComponents());
			else basicProds.add((IBasicProduction) prod);
		}
		return asLambdaFromBasicProd(basicProds);
	}

	public ILambdaExpression asLambdaFromBasicProd(List<IBasicProduction> nextProductions) {
		ILambdaExpression lambda = semanticRule();
		if (value.isAbstract()) {
			int nbOfRemainingValueVars = value.getVariables().size();
			List<IBasicProduction> remainingProd = new ArrayList<>(nextProductions);
			Iterator<IBasicProduction> prodIte = nextProductions.iterator();
			while (nbOfRemainingValueVars > 0 && prodIte.hasNext()) {
				IBasicProduction nextProd = prodIte.next();
				AVariable prodVar = nextProd.getVariable();
				if (value.getVariables().contains(prodVar)) {
					remainingProd.remove(nextProd);
					lambda.setArgument(prodVar, nextProd.asLambdaFromBasicProd(remainingProd));
					nbOfRemainingValueVars--;
				}
			}
		}
		return lambda;
	}

	@Override
	public ICompositeProduction compose(IBasicProduction other) {
		if (other.getSource().equals(getSource())
				&& other.getTarget().equals(getTarget()))
			return new CompositeProduction(this, other);
		else return null;
	}

	@Override
	public boolean isBlank() {
		return false;
	}

}
