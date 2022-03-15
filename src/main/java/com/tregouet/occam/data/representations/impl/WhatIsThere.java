package com.tregouet.occam.data.representations.impl;

import java.util.Set;

import com.tregouet.occam.data.automata.tapes.ITapeSet;
import com.tregouet.occam.data.partitions.IPartition;
import com.tregouet.occam.data.preconcepts.impl.WhatIsTherePreconcept;
import com.tregouet.occam.data.representations.IConcept;
import com.tregouet.occam.data.representations.transitions.IApplication;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;

public class WhatIsThere extends Concept implements IConcept {

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
	public void loadTransitionRules(IConceptTransition transitions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean evaluate(ITapeSet tapeSet) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void generateOutputLanguage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getRank() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOperative() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void mergeTapeSets() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void proceedTransitions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRank(int rank) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSubconceptsDistinctiveFeaturesSaliency() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSpeciesAlignableDifferencesSaliency() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSaliencies() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<IApplication> getApplications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<IConceptTransition> getConceptTransitions() {
		// TODO Auto-generated method stub
		return null;
	}

}
