package com.tregouet.occam.data.concepts;

import java.util.List;

import com.tregouet.occam.data.abstract_machines.transitions.ITransition;

public interface IGenusDifferentiaDefinition {
	
	List<ITransition> getDifferentiae();
	
	IConcept getGenus();
	
	IConcept getSpecies();

}
