package com.tregouet.occam.data.abstract_machines.transitions;

public interface IReframer extends ICostedTransition {
	
	Integer getComplementedConceptsID();
	
	boolean isConnector();

}
