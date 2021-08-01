package com.tregouet.occam.data.operators;

import java.util.List;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.IConstruct;

public interface IProduction {
	
	int hashCode();
	
	ILambdaExpression asLambda(List<IProduction> nextProductions);
	
	/**
	 * Mainly useful to explain what a Production is meant to be. Never actually used.
	 * @param construct
	 * @return
	 */
	IConstruct derive(IConstruct construct);
	
	boolean derives(AVariable var);
	
	/**
	 * Mainly useful to explain what a Production is meant to be. Never actually used.
	 * @param construct
	 * @return
	 */
	IConstruct doAbstract(IConstruct construct);
	
	boolean equals(Object o);
	
	ICategory getGenus();
	
	ICategory getInstance();
	
	IIntentAttribute getOperatorInput();
	
	IIntentAttribute getOperatorOutput();
	
	IConstruct getValue();
	
	AVariable getVariable();
	
	ILambdaExpression semanticRule();
	
	void setOperator(IOperator operator);
	
	IOperator getOperator();
	
	String toString();

}
