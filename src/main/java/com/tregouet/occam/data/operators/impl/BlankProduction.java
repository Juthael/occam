package com.tregouet.occam.data.operators.impl;

import java.util.List;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.operators.IBasicProduction;
import com.tregouet.occam.data.operators.ILambdaExpression;
import com.tregouet.occam.data.operators.IProduction;

public class BlankProduction extends BasicProduction implements IBasicProduction {

	private static final long serialVersionUID = -1489727700831533797L;

	public BlankProduction(IIntentAttribute operatorInput, IIntentAttribute operatorOutput) {
		super(operatorInput, operatorOutput);
	}
	
	@Override
	public ILambdaExpression asLambda(List<IProduction> nextProductions) {
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		return (this == obj);
	}
	
	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}

	@Override
	public boolean isBlank() {
		return true;
	}

	@Override
	public ILambdaExpression semanticRule() {
		return null;
	}
	
	@Override
	public String toString() {
		return "inheritance";  
	}
	
	@Override
	public IProduction rebut(IConcept complementaryConcept) {
		return null;
	}

}
