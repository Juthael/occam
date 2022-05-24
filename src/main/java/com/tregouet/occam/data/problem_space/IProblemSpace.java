package com.tregouet.occam.data.problem_space;

import java.util.List;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorer;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

public interface IProblemSpace {

	DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> getProblemSpaceGraph();
	
	List<IContextObject> getContext();
	
	/**
	 * 
	 * @param representationID
	 * @return null if no representation has this iD, false if already active, true otherwise
	 */
	Boolean display(int representationID);
	
	/**
	 * 
	 * @param representationID
	 * @return null if no representation has this iD, false if not expandable, true otherwise
	 */
	Boolean deepen(int representationID);
	
	IRepresentation getActiveRepresentation();
	
	public static ProblemSpaceExplorer problemSpaceExplorer() {
		return GeneratorsAbstractFactory.INSTANCE.getProblemSpaceExplorer();
	}

}