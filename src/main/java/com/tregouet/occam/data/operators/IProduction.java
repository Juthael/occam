package com.tregouet.occam.data.operators;

import java.util.List;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.IConstruct;

public interface IProduction {
	
	/**
	 * Mainly useful to explain what a Production is meant to be. Never actually used.
	 * @param construct
	 * @return
	 */
	IConstruct doAbstract(IConstruct construct);
	
	AVariable getVariable();
	
	IConstruct getValue();
	
	boolean derives(AVariable var);
	
	/**
	 * Mainly useful to explain what a Production is meant to be. Never actually used.
	 * @param construct
	 * @return
	 */
	IConstruct derive(IConstruct construct);
	
	ILambdaExpression semanticRule();
	
	ILambdaExpression asLambda(List<IProduction> nextProductions);
	
	void setOperator(IOperator operator);
	
	ICategory getGenus();
	
	ICategory getInstance();

}
