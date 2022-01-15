package com.tregouet.occam.data.languages.specific.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.languages.ISymbol;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.impl.Construct;
import com.tregouet.occam.data.languages.lambda.ILambdaExpression;
import com.tregouet.occam.data.languages.lambda.impl.LambdaExpression;
import com.tregouet.occam.data.languages.specific.ISimpleEdgeProduction;
import com.tregouet.occam.data.languages.specific.ICompositeEdgeProduction;
import com.tregouet.occam.data.languages.specific.ICompositeProduction;
import com.tregouet.occam.data.languages.specific.IEdgeProduction;
import com.tregouet.occam.data.languages.specific.IBasicProduction;

public class SimpleEdgeProduction extends EdgeProduction implements ISimpleEdgeProduction {

	private static final long serialVersionUID = 1701074226278101143L;
	private final IBasicProduction basicProduction;
	
	public SimpleEdgeProduction(AVariable variable, IConstruct value, IDenotation operatorInput, 
			IDenotation operatorOutput) {
		super(operatorInput, operatorOutput);
		basicProduction = new BasicProduction(variable, value);
		
	}
	
	protected SimpleEdgeProduction(IDenotation operatorInput, IDenotation operatorOutput) {
		super(operatorInput, operatorOutput);
		basicProduction = null;;
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
	public boolean isBlank() {
		return false;
	}

	@Override
	public boolean isVariableSwitcher() {
		return variableSwitcher;
	}



	@Override
	public IEdgeProduction switchVariableOrReturnNull(IEdgeProduction varSwitcher) {
		if (this.getTargetDenotationSet().equals(varSwitcher.getSourceDenotationSet()) 
				&& this.getTarget().equals(varSwitcher.getSource()) && varSwitcher instanceof ISimpleEdgeProduction) {
			ISimpleEdgeProduction basicSwitcher = (ISimpleEdgeProduction) varSwitcher;
			if (this.variable.equals(basicSwitcher.getValue().getListOfSymbols().get(0))) {
				return new SimpleEdgeProduction(
						basicSwitcher.getVariable(), this.value, this.getSource(), varSwitcher.getTarget());
			}
			return null;
		}
		return null;
	}

	@Override
	public ILambdaExpression asLambdaFromBasicProd(List<IBasicProduction> nextProductions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICompositeProduction compose(IBasicProduction basicComponent) {
		// TODO Auto-generated method stub
		return null;
	}

}
