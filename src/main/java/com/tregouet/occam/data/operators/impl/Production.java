package com.tregouet.occam.data.operators.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.IConstruct;
import com.tregouet.occam.data.constructs.ISymbol;
import com.tregouet.occam.data.constructs.impl.Construct;
import com.tregouet.occam.data.operators.ILambdaExpression;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.data.operators.IProduction;

public class Production extends DefaultEdge implements IProduction {

	private static final long serialVersionUID = 1701074226278101143L;
	private final AVariable variable;
	private final IConstruct value;
	private final IIntentAttribute operatorInput;
	private final IIntentAttribute operatorOutput;
	private IOperator operator;
	
	public Production(AVariable variable, IConstruct value, IIntentAttribute operatorInput, IIntentAttribute operatorOutput) {
		this.variable = variable;
		this.value = value;
		this.operatorInput = operatorInput;
		this.operatorOutput = operatorOutput;
	}
	
	protected Production(IIntentAttribute operatorInput, IIntentAttribute operatorOutput) {
		variable = null;
		value = null;
		this.operatorInput = operatorInput;
		this.operatorOutput = operatorOutput;
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
	public ICategory getGenus() {
		return operatorOutput.getCategory();
	}

	@Override
	public ICategory getInstance() {
		return operatorInput.getCategory();
	}

	public IIntentAttribute getOperatorInput() {
		return operatorInput;
	}

	public IIntentAttribute getOperatorOutput() {
		return operatorOutput;
	}

	@Override
	public IConstruct getValue() {
		return value;
	}
	
	@Override
	public AVariable getVariable() {
		return variable;
	}

	@Override
	public ILambdaExpression semanticRule() {
		return new LambdaExpression(value);
	}

	@Override
	public void setOperator(IOperator operator) {
		this.operator = operator;
	}

	@Override
	public IOperator getOperator() {
		return operator;
	}
	
	@Override
	public String toString() {
		return "[" + variable.toString() + " ::= " + value.toString() + "]";  
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		result = prime * result + ((variable == null) ? 0 : variable.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Production other = (Production) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (variable == null) {
			if (other.variable != null)
				return false;
		} else if (!variable.equals(other.variable))
			return false;
		return true;
	}

	@Override
	public IIntentAttribute getSource() {
		return operatorInput;
	}

	@Override
	public IIntentAttribute getTarget() {
		return operatorOutput;
	}

	@Override
	public ICategory getSourceCategory() {
		return operatorInput.getCategory();
	}

	@Override
	public ICategory getTargetCategory() {
		return operatorOutput.getCategory();
	}

	@Override
	public String getLabel() {
		return toString();
	}

}
