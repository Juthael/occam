package com.tregouet.occam.data.operators;

import java.util.List;

import com.tregouet.occam.transition_function.IState;

public interface IReframer extends ITransition {
	
	String getReframer();

	List<Integer> getComplementedConceptsIDs();

}
