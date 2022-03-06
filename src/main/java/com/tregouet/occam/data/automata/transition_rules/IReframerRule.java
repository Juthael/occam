package com.tregouet.occam.data.automata.transition_rules;

public interface IReframerRule extends ITransitionRule {
	
	Integer getComplementedStateID();
	
	boolean isConnector();

}
