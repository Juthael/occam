package com.tregouet.occam.data.abstract_machines.transitions;

import java.util.List;

public interface IReframer extends ICostedTransition {
	
	List<Integer> getComplementedConceptsIDs();

	String getReframer();

}
