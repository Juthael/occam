package com.tregouet.occam.data.problem_space;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorer;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.descriptions.properties.AbstractDifferentiae;
import com.tregouet.occam.data.problem_space.states.evaluation.facts.IFact;
import com.tregouet.occam.data.problem_space.states.transitions.AConceptTransitionSet;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;
import com.tregouet.tree_finder.data.Tree;

public interface IProblemSpace {

	DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> getProblemSpaceGraph();
	
	Tree<Integer, AbstractDifferentiae> getActiveRepresentationDescriptionGraph();
	
	double[][] getAsymmetricalSimilarityMatrixOfActiveRepresentation();
	
	double[] getTypicalityVectorOfActiveRepresentation();
	
	DirectedAcyclicGraph<Integer, AConceptTransitionSet> getTransitionFunctionGraphOfActiveRepresentation();
	
	double[][] getSimilarityMatrixOfActiveRepresentation();
	
	List<IContextObject> getContext();
	
	/**
	 * 
	 * @param representationID
	 * @return null if no representation has this iD, false if already active, true otherwise
	 */
	Boolean explore(int representationID);
	
	/**
	 * 
	 * @param representationID
	 * @return null if no representation has this iD, false if not expandable, true otherwise
	 */
	Boolean expand(int representationID);
	
	Integer getActiveRepresentationID();	
	
	Map<Integer, Set<IFact>> particularToAcceptedFactsInActiveRepresentation();
	
	public static ProblemSpaceExplorer problemSpaceExplorer() {
		return GeneratorsAbstractFactory.INSTANCE.getProblemSpaceExplorer();
	}

}
