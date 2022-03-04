package com.tregouet.occam.data.abstract_machines.states;

import com.tregouet.occam.data.abstract_machines.tapes.ITapeSet;
import com.tregouet.occam.data.abstract_machines.transition_rules.ITransitionRule;
import com.tregouet.occam.data.denotations.IConcept;

public interface IState {
	
	static final int ABS_STATE = IConcept.ABSURDITY;
	static final int OBJ_STATE = IConcept.OBJECT;
	static final int CAT_STATE = IConcept.CONTEXT_SUBSET;
	static final int TRUISM_STATE = IConcept.TRUISM;
	static final int OC_STATE = IConcept.ONTOLOGICAL_COMMITMENT;
	
	boolean acceptInput(ITapeSet tapeSet);
	
	void loadTransitionRule(ITransitionRule transitionRule);
	
	@Override
	boolean equals(Object o);
	
	void generateOutputLanguage();
	
	IConcept getAssociatedDenotationSet();
	
	int getRank();
	
	int iD();
	
	int getStateType();
	
	@Override
	int hashCode();
	
	boolean isActive();
	
	boolean isOperative();
	
	void mergeTapeSetsWithSameInput();
	
	void proceedTransitions();
	
	void setRank(int rank);

}
