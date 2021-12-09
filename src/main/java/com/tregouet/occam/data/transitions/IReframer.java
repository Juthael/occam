package com.tregouet.occam.data.transitions;

import java.util.List;

public interface IReframer extends ITransition {
	
	List<Integer> getComplementedConceptsIDs();

	String getReframer();

}
