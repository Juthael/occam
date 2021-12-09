package com.tregouet.occam.data.abstract_machines.transitions;

import java.util.List;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;

public interface IProduction {
	
	ICompositeProduction compose(IBasicProduction basicComponent);
	
	boolean derives(AVariable var);
	
	@Override
	boolean equals(Object o);
	
	IConcept getGenus();
	
	IConcept getInstance();
	
	String getLabel();
	
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
