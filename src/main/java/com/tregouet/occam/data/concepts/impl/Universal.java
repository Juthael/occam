package com.tregouet.occam.data.concepts.impl;

import java.util.List;
import java.util.Set;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IDifferentiae;
import com.tregouet.occam.data.concepts.IUniversal;
import com.tregouet.occam.data.concepts.transitions.IApplication;
import com.tregouet.occam.data.concepts.transitions.IConceptTransition;
import com.tregouet.occam.data.partitions.IPartition;
import com.tregouet.occam.data.preconcepts.IPreconcept;

public class Universal extends Concept implements IUniversal {

	private List<IConcept> species;
	private IDifferentiae[] speciesDiff;
	
	public Universal(IPreconcept preconcept) {
		super(preconcept);
		// TODO Auto-generated constructor stub
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
