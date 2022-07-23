package com.tregouet.occam.data.problem_space;

import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorer;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

public interface IProblemSpace {

	Boolean develop();

	/**
	 *
	 * @param representationID
	 * @return null if no representation has this iD, false if not expandable, true otherwise
	 */
	Boolean develop(int representationID);
	
	/**
	 *
	 * @param representationID
	 * @return null if no representation has been found, false if problem space is left unchanged, true otherwise
	 */
	Boolean develop(List<Integer> representationIDs);

	/**
	 *
	 * @param representationID
	 * @return null if no representation has this iD, false if already active, true otherwise
	 */
	Boolean display(int representationID);

	IRepresentation getActiveRepresentation();

	double[][] getAsymmetricalSimilarityMatrix();

	List<IContextObject> getContext();

	double[][] getDifferenceMatrix();

	DirectedAcyclicGraph<IConcept, IIsA> getLatticeOfConcepts();

	DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> getProblemSpaceGraph();

	String[][] getReferenceMatrix();

	double[][] getSimilarityMatrix();

	double[] getTypicalityVector();

	Boolean restrictTo(Set<Integer> representationIDs);

	public static ProblemSpaceExplorer problemSpaceExplorer() {
		return BuildersAbstractFactory.INSTANCE.getProblemSpaceExplorer();
	}

}
