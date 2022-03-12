package com.tregouet.occam.data.concepts.impl;

import java.util.Set;

import com.tregouet.occam.data.automata.states.IState;
import com.tregouet.occam.data.concepts.transitions.IConceptTransition;
import com.tregouet.occam.data.partitions.IPartition;
import com.tregouet.occam.data.preconcepts.impl.WhatIsTherePreconcept;

public class WhatIsThere extends Concept implements IState {

	public static final WhatIsThere INSTANCE = new WhatIsThere();
	
	private WhatIsThere() {
		super(WhatIsTherePreconcept.INSTANCE);
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
	public int getLowestSubordinateID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isUniversal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void loadTransitionRules(IConceptTransition transitions) {
		// TODO Auto-generated method stub
		
	}

}
