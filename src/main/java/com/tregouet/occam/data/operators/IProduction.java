package com.tregouet.occam.data.operators;

import java.util.List;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.IConstruct;

public interface IProduction {
	
	int hashCode();
	
	boolean derives(AVariable var);
	
	boolean equals(Object o);
	
	ICategory getGenus();
	
	ICategory getInstance();
	
	List<IConstruct> getValues();
	
	List<AVariable> getVariables();
	
	void setOperator(IOperator operator);
	
	IOperator getOperator();
	
	String toString();
	
	IIntentAttribute getSource();
	
	IIntentAttribute getTarget();
	
	ICategory getSourceCategory();
	
	ICategory getTargetCategory();
	
	String getLabel();
	
	ICompositeProduction compose(IBasicProduction basicComponent);

}
