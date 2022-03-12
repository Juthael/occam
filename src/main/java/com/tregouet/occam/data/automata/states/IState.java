package com.tregouet.occam.data.automata.states;

import com.tregouet.occam.data.automata.tapes.ITapeSet;

public interface IState {
	
	boolean acceptInput(ITapeSet tapeSet);
	
	@Override
	boolean equals(Object o);
	
	void generateOutputLanguage();
	
	int getRank();
	
	int iD();
	
	@Override
	int hashCode();
	
	boolean isActive();
	
	boolean isOperative();
	
	void mergeTapeSets();
	
	void proceedTransitions();
	
	void setRank(int rank);

}
