package com.tregouet.occam.data.abstract_machines.states;

import com.tregouet.occam.data.abstract_machines.tapes.ITapeSet;
import com.tregouet.occam.data.abstract_machines.transition_rules.ITransitionRule;
import com.tregouet.occam.data.denotations.IDenotationSet;

public interface IState {
	
	static final int ABS_STATE = IDenotationSet.ABSURDITY;
	static final int OBJ_STATE = IDenotationSet.OBJECT;
	static final int CAT_STATE = IDenotationSet.CONTEXT_SUBSET;
	static final int TRUISM_STATE = IDenotationSet.TRUISM;
	static final int OC_STATE = IDenotationSet.ONTOLOGICAL_COMMITMENT;
	
	boolean acceptInput(ITapeSet tapeSet);
	
	void loadTransitionRule(ITransitionRule transitionRule);
	
	@Override
	boolean equals(Object o);
	
	void generateOutputLanguage();
	
	IDenotationSet getAssociatedDenotationSet();
	
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
