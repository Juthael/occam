package com.tregouet.occam.transition_function;

import java.util.Set;

import com.tregouet.occam.compiler.ITapeSet;
import com.tregouet.occam.data.categories.IIntentAttribute;
import com.tregouet.occam.data.constructs.IContextObject;
import com.tregouet.occam.data.operators.IOperator;

public interface IState {
	
	Set<IIntentAttribute> getIntent();
	
	Set<IContextObject> getExtent();
	
	int getStateID();
	
	int getStateType();
	
	boolean acceptInput(ITapeSet tapeSet);
	
	void proceedTransitions();
	
	int getRank();
	
	void generateOutputLanguage();
	
	boolean isActive();
	
	boolean isOperative();
	
	void addTransition(IOperator operator);
	
	void setRank(int rank);
	
	void mergeTapeSetsWithSameInput();

}
