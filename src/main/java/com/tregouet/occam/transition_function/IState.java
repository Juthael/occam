package com.tregouet.occam.transition_function;

import java.util.Set;

import com.tregouet.occam.compiler.ITapeSet;
import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IOperator;

public interface IState {
	
	static final int START_STATE = ICategory.ABSURDITY;
	static final int OBJ_STATE = ICategory.OBJECT;
	static final int CAT_STATE = ICategory.SUBSET_CAT;
	static final int TRUISM_STATE = ICategory.TRUISM;
	static final int PREACCEPT_STATE = ICategory.TRUISM_TRUISM;
	static final int ACCEPT_STATE = ICategory.ONTOLOGICAL_COMMITMENT;
	
	int hashCode();
	
	boolean acceptInput(ITapeSet tapeSet);
	
	void addTransition(IOperator operator);
	
	boolean equals(Object o);
	
	void generateOutputLanguage();
	
	Set<IContextObject> getExtent();
	
	Set<IIntentAttribute> getIntent();
	
	int getRank();
	
	int getStateID();
	
	int getStateType();
	
	boolean isActive();
	
	boolean isOperative();
	
	void mergeTapeSetsWithSameInput();
	
	void proceedTransitions();
	
	void setRank(int rank);

}
