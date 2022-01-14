package com.tregouet.occam.data.languages.specific;

import java.util.List;

import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotation;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.generic.IConstruct;
import com.tregouet.occam.data.languages.generic.ISymbol;

public interface IProduction extends ISymbol {
	
	ICompositeProduction compose(IBasicProduction basicComponent);
	
	boolean derives(AVariable var);
	
	@Override
	boolean equals(Object o);
	
	IDenotationSet getGenusDenotationSet();
	
	String getLabel();
	
	IDenotation getSource();
	
	IDenotationSet getSourceDenotationSet();
	
	IDenotationSet getSpeciesDenotationSet();
	
	IDenotation getTarget();
	
	IDenotationSet getTargetDenotationSet();
	
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
