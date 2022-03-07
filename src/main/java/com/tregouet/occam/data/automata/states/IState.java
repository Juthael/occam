package com.tregouet.occam.data.automata.states;

import com.tregouet.occam.data.automata.tapes.ITapeSet;
import com.tregouet.occam.data.automata.transition_rules.ITransitionRule;
import com.tregouet.occam.data.preconcepts.IPreconcept;

public interface IState {
	
	static final int ABS_STATE = IPreconcept.ABSURDITY;
	static final int OBJ_STATE = IPreconcept.OBJECT;
	static final int CAT_STATE = IPreconcept.CONTEXT_SUBSET;
	static final int TRUISM_STATE = IPreconcept.TRUISM;
	static final int OC_STATE = IPreconcept.ONTOLOGICAL_COMMITMENT;
	
	boolean acceptInput(ITapeSet tapeSet);
	
	void loadTransitionRule(ITransitionRule transitionRule);
	
	@Override
	boolean equals(Object o);
	
	void generateOutputLanguage();
	
	IPreconcept getAssociatedDenotationSet();
	
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
