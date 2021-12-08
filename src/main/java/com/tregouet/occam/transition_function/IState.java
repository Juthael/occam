package com.tregouet.occam.transition_function;

import java.util.Set;

import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IIntentAttribute;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.finite_automaton.ITapeSet;

public interface IState {
	
	static final int START_STATE = IConcept.ABSURDITY;
	static final int OBJ_STATE = IConcept.SINGLETON;
	static final int CAT_STATE = IConcept.SUBSET_CONCEPT;
	static final int TRUISM_STATE = IConcept.TRUISM;
	static final int ACCEPT_STATE = IConcept.ONTOLOGICAL_COMMITMENT;
	
	boolean acceptInput(ITapeSet tapeSet);
	
	void addTransition(IOperator operator);
	
	@Override
	boolean equals(Object o);
	
	void generateOutputLanguage();
	
	IConcept getAssociatedConcept();
	
	Set<IIntentAttribute> getInputLanguage();
	
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
	
	void frame(IFrame other);

}
