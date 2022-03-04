package com.tregouet.occam.data.abstract_machines.transition_rules;

import com.tregouet.occam.data.abstract_machines.states.IState;
import com.tregouet.occam.data.languages.generic.AVariable;
import com.tregouet.occam.data.languages.specific.IBasicProductionAsEdge;

public interface IInputConfiguration {
	
	IState getInputState();
	
	IBasicProductionAsEdge getInputSymbol();
	
	AVariable getInputStackSymbol();
	
	@Override
	boolean equals(Object o);
	
	@Override
	int hashCode();

}
