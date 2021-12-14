package com.tregouet.occam.data.abstract_machines.states;

import java.util.Set;

import com.tregouet.occam.data.abstract_machines.tapes.ITapeSet;
import com.tregouet.occam.data.abstract_machines.transitions.IOperator;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIntentConstruct;

public interface IState {
	
	static final int ABS_STATE = IConcept.ABSURDITY;
	static final int OBJ_STATE = IConcept.SINGLETON;
	static final int CAT_STATE = IConcept.SUBSET_CONCEPT;
	static final int TRUISM_STATE = IConcept.TRUISM;
	static final int OC_STATE = IConcept.ONTOLOGICAL_COMMITMENT;
	
	boolean acceptInput(ITapeSet tapeSet);
	
	void addTransition(IOperator operator);
	
	@Override
	boolean equals(Object o);
	
	void generateOutputLanguage();
	
	IConcept getAssociatedConcept();
	
	Set<IIntentConstruct> getInputLanguage();
	
	int getRank();
	
	int getStateID();
	
	int getStateType();
	
	@Override
	int hashCode();
	
	boolean isActive();
	
	boolean isOperative();
	
	void mergeTapeSetsWithSameInput();
	
	void proceedTransitions();
	
	void setRank(int rank);

}
