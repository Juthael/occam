package com.tregouet.occam.data.automata.states;

public interface IState {
	
	@Override
	boolean equals(Object o);
	
	int iD();
	
	@Override
	int hashCode();

}
