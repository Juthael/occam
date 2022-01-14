package com.tregouet.occam.data.abstract_machines.transitions.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.abstract_machines.transitions.IBasicProduction;
import com.tregouet.occam.data.abstract_machines.transitions.ICompositeProduction;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.ISymbol;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.data.languages.lambda.ILambdaExpression;
import com.tregouet.occam.data.languages.lambda.impl.LambdaExpression;

public class BasicProduction extends Production implements IBasicProduction {

	private static final long serialVersionUID = 1701074226278101143L;
	private final AVariable variable;
	private final IConstruct value;
	private boolean variableSwitcher;
	
	public BasicProduction(AVariable variable, IConstruct value, IDenotation operatorInput, 
			IDenotation operatorOutput) {
		super(operatorInput, operatorOutput);
		this.variable = variable;
		this.value = value;
		variableSwitcher = value.getNbOfTerminals() == 0;
	}
	
	protected BasicProduction(IDenotation operatorInput, IDenotation operatorOutput) {
		super(operatorInput, operatorOutput);
		variable = null;
		value = null;
		variableSwitcher = false;
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

	@Override
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
		if (other.getSource().equals(this.getSource())
				&& other.getTarget().equals(this.getTarget()))
			return new CompositeProduction(this, other);
		return null;
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
	
	@Override
	public String getLabel() {
		return toString();
	}

	@Override
	public IConstruct getValue() {
		return value;
	}

	@Override
	public List<IConstruct> getValues() {
		return new ArrayList<>(Arrays.asList(new IConstruct[] {value}));
	}

	@Override
	public AVariable getVariable() {
		return variable;
	}
	
	@Override
	public List<AVariable> getVariables() {
		return new ArrayList<>(Arrays.asList(new AVariable[] {variable}));
	}

	@Override
	public boolean isBlank() {
		return false;
	}

	@Override
	public boolean isVariableSwitcher() {
		return variableSwitcher;
	}

	@Override
	public ILambdaExpression semanticRule() {
		return new LambdaExpression(value);
	}

	@Override
	public IProduction switchVariableOrReturnNull(IProduction varSwitcher) {
		if (this.getTargetDenotationSet().equals(varSwitcher.getSourceDenotationSet()) 
				&& this.getTarget().equals(varSwitcher.getSource()) && varSwitcher instanceof IBasicProduction) {
			IBasicProduction basicSwitcher = (IBasicProduction) varSwitcher;
			if (this.variable.equals(basicSwitcher.getValue().getListOfSymbols().get(0))) {
				return new BasicProduction(
						basicSwitcher.getVariable(), this.value, this.getSource(), varSwitcher.getTarget());
			}
			return null;
		}
		return null;
	}

	@Override
	public String toString() {
		return "[" + variable.toString() + " ::= " + value.toString() + "]";  
	}

}
