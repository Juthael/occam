package com.tregouet.occam.data.abstract_machines.transition_rules;

public interface IReframerRule extends ITransitionRule {
	
	Integer getComplementedStateID();
	
	boolean isConnector();

}
