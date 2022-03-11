package com.tregouet.occam.data.concepts.impl;

import java.util.Set;

import com.tregouet.occam.data.automata.states.impl.State;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.transitions.IConceptTransition;
import com.tregouet.occam.data.partitions.IPartition;
import com.tregouet.occam.data.preconcepts.IPreconcept;

public class Concept extends State implements IConcept {
	
	

	public Concept(IPreconcept preconcept) {
		super(preconcept);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setSpeciesDistinctiveFeaturesSaliency() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSpeciesAlignableDifferencesSaliency() {
		// TODO Auto-generated method stub

	}

	@Override
	public IPartition getConceptPartition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IPartition> getSubPartitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IPartition> getAllPartitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLowestSubordinateID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void loadTransitionRules(IConceptTransition transitions) {
		// TODO Auto-generated method stub	
	}

}
