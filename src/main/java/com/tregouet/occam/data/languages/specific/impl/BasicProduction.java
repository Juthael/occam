package com.tregouet.occam.data.languages.specific.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.languages.ISymbol;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.data.languages.lambda.ILambdaExpression;
import com.tregouet.occam.data.languages.lambda.impl.LambdaExpression;
import com.tregouet.occam.data.languages.specific.IBasicProduction;
import com.tregouet.occam.data.languages.specific.ICompositeProduction;
import com.tregouet.occam.data.languages.specific.IProduction;

public class BasicProduction extends Production implements IBasicProduction {

	private AVariable variable;
	private final IConstruct value;
	boolean variableSwitcher;
	
	public BasicProduction(AVariable variable, IConstruct value) {
		super(variable);
		this.value = value;
		variableSwitcher = value.getNbOfTerminals() == 0;
	}
	
	@Override
	public IConstruct getValue() {
		return value;
	}

	@Override
	public boolean isVariableSwitcher() {
		return variableSwitcher;
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
				if (nextProd instanceof IBasicProduction) {
					IBasicProduction nextBasicProd = (IBasicProduction) nextProd;
					if (value.getVariables().contains(prodVar)) {
						remainingProd.remove(nextBasicProd);
						lambda.setArgument(prodVar, nextBasicProd.asLambda(remainingProd));
						nbOfRemainingValueVars--;
					}
				}
				else {
					//CAUTION : a composite production must be the last element of a property
					ICompositeProduction nextCompositeProd = (ICompositeProduction) nextProd;
					if (value.getVariables().contains(prodVar)) {
						remainingProd.remove(nextCompositeProd);
						lambda.setArgument(prodVar, nextCompositeProd.asLambda());
						nbOfRemainingValueVars--;
					}
				}
			}
		}
		return lambda;
	}

	@Override
	public ICompositeProduction combine(IBasicProduction basicComponent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		return "[" + variable.toString() + " ::= " + value.toString() + "]";  
	}
	
	@Override
	public ILambdaExpression semanticRule() {
		return new LambdaExpression(value);
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

}
