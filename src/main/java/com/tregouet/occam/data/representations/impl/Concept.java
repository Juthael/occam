package com.tregouet.occam.data.representations.impl;

import java.util.HashSet;
import java.util.Set;

import com.tregouet.occam.data.automata.tapes.ITapeSet;
import com.tregouet.occam.data.partitions.IPartition;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.representations.ConceptType;
import com.tregouet.occam.data.representations.IConcept;
import com.tregouet.occam.data.representations.transitions.IApplication;
import com.tregouet.occam.data.representations.transitions.IConceptTransition;

public abstract class Concept implements IConcept {
	
	protected final IPreconcept preconcept;
	protected Integer rank = null;
	protected Set<ITapeSet> tapeSets = new HashSet<>();
	
	public Concept(IPreconcept preconcept) {
		this.preconcept = preconcept;
	}

	@Override
	abstract public Set<IPartition> getAllPartitions();
	
	@Override
	public int getID() {
		return preconcept.getID();
	}

	@Override
	abstract public IPartition getConceptPartition();

	@Override
	abstract public Set<IPartition> getSubPartitions();

	@Override
	abstract public int getLowestSubordinateID();

	@Override
	public ConceptType type() {
		return getPreconcept().type();
	}

	@Override
	public IPreconcept getPreconcept() {
		return preconcept;
	}

	@Override
	public int iD() {
		return preconcept.getID();
	}

	@Override
	abstract public void loadTransitionRule(IConceptTransition transition);
	
	@Override
	abstract public void loadTransitionRules(Set<IConceptTransition> transitions);

	@Override
	public boolean evaluate(ITapeSet tapeSet) {
		// NOT IMPLEMENTED YET
		return false;
	}

	@Override
	public void generateOutputLanguage() {
		// NOT IMPLEMENTED YET
	}

	@Override
	public int getRank() {
		if (rank != null)
			return rank;
		return 0;
	}

	@Override
	public boolean isActive() {
		return !tapeSets.isEmpty();
	}

	@Override
	abstract public boolean isOperative();

	@Override
	public void mergeTapeSets() {
		// NOT IMPLEMENTED YET
	}

	@Override
	abstract public void proceedTransitions();

	@Override
	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public void init() {
		tapeSets = new HashSet<>();
	}

	@Override
	abstract public void setSubconceptsDistinctiveFeaturesSalience();

	@Override
	abstract public void setSpeciesAlignableDifferencesSalience();

	@Override
	abstract public void setSaliences();

	@Override
	abstract public Set<IApplication> getApplications();

	@Override
	abstract public Set<IConceptTransition> getConceptTransitions();

}
