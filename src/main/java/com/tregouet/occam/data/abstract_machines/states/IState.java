package com.tregouet.occam.data.abstract_machines.states;

import java.util.Set;

import com.tregouet.occam.data.abstract_machines.tapes.ITapeSet;
import com.tregouet.occam.data.abstract_machines.transitions.IOperator;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotation;

public interface IState {
	
	static final int ABS_STATE = IDenotationSet.ABSURDITY;
	static final int OBJ_STATE = IDenotationSet.OBJECT;
	static final int CAT_STATE = IDenotationSet.CONTEXT_SUBSET;
	static final int TRUISM_STATE = IDenotationSet.TRUISM;
	static final int OC_STATE = IDenotationSet.ONTOLOGICAL_COMMITMENT;
	
	boolean acceptInput(ITapeSet tapeSet);
	
	void addTransition(IOperator operator);
	
	@Override
	boolean equals(Object o);
	
	void generateOutputLanguage();
	
	IDenotationSet getAssociatedDenotationSet();
	
	Set<IDenotation> getInputLanguage();
	
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
