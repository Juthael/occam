package com.tregouet.occam.data.automata.transition_rules;

public interface IReframerRule extends ITransition {
	
	Integer getComplementedStateID();
	
	boolean isConnector();

}
