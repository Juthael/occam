package com.tregouet.occam.data.automata.states;

public interface IState {
	
	@Override
	boolean equals(Object o);
	
	void generateOutputLanguage();
	
	int getRank();
	
	int iD();
	
	@Override
	int hashCode();
	
	boolean isActive();
	
	boolean isOperative();
	
	void proceedTransitions();
	
	void setRank(int rank);
	
	void init();

}
