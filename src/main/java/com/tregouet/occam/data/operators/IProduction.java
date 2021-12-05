package com.tregouet.occam.data.operators;

import java.util.List;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.constructs.AVariable;
import com.tregouet.occam.data.constructs.IConstruct;

public interface IProduction {
	
	ICompositeProduction compose(IBasicProduction basicComponent);
	
	boolean derives(AVariable var);
	
	@Override
	boolean equals(Object o);
	
	IConcept getGenus();
	
	IConcept getInstance();
	
	String getLabel();
	
	//IOperator getOperator();
	
	IIntentAttribute getSource();
	
	IConcept getSourceCategory();
	
	IIntentAttribute getTarget();
	
	IConcept getTargetCategory();
	
	List<IConstruct> getValues();
	
	List<AVariable> getVariables();
	
	@Override
	int hashCode();
	
	boolean isBlank();
	
	//void setOperator(IOperator operator);
	
	boolean isVariableSwitcher();
	
	/**
	 * 
	 * @param varSwitcher a production such as varSwitcher.isVariableSwitcher() == true
	 * @return
	 */
	IProduction switchVariableOrReturnNull(IProduction varSwitcher);
	
	@Override
	String toString();

}
