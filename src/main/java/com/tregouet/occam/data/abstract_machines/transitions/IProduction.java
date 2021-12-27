package com.tregouet.occam.data.abstract_machines.transitions;

import java.util.List;

import com.tregouet.occam.alg.calculators.costs.ICosted;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIntentConstruct;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;

public interface IProduction extends ICosted {
	
	ICompositeProduction compose(IBasicProduction basicComponent);
	
	boolean derives(AVariable var);
	
	@Override
	boolean equals(Object o);
	
	IConcept getGenus();
	
	String getLabel();
	
	IIntentConstruct getSource();
	
	IConcept getSourceCategory();
	
	IConcept getSpecies();
	
	IIntentConstruct getTarget();
	
	IConcept getTargetConcept();
	
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
	
	void setCost(double cost);
	
	Double getCost();	

}
