package com.tregouet.occam.data.operators;

import java.util.List;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.IConstruct;

public interface IProduction {
	
	ICompositeProduction compose(IBasicProduction basicComponent);
	
	boolean derives(AVariable var);
	
	@Override
	boolean equals(Object o);
	
	ICategory getGenus();
	
	ICategory getInstance();
	
	String getLabel();
	
	IOperator getOperator();
	
	IIntentAttribute getSource();
	
	ICategory getSourceCategory();
	
	IIntentAttribute getTarget();
	
	ICategory getTargetCategory();
	
	List<IConstruct> getValues();
	
	List<AVariable> getVariables();
	
	@Override
	int hashCode();
	
	boolean isBlank();
	
	void setOperator(IOperator operator);
	
	@Override
	String toString();

}
