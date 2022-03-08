package com.tregouet.occam.data.automata.transitions;

public interface IReframerRule extends ITransition {
	
	Integer getComplementedStateID();
	
	boolean isConnector();

}
