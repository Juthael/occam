package com.tregouet.occam.data.logical_structures.automata.states;

public interface IState {

	@Override
	boolean equals(Object o);

	@Override
	int hashCode();

	int iD();

}
