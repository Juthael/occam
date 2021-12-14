package com.tregouet.occam.data.concepts;

import java.util.List;

import com.tregouet.occam.data.abstract_machines.transitions.ITransition;

public abstract class IGenusDifferentiaDefinition extends IIsA {
	
	private static final long serialVersionUID = -1660518980107230824L;

	abstract public List<ITransition> getDifferentiae();
	
	abstract public IConcept getGenus();
	
	abstract public IConcept getSpecies();

}
