package com.tregouet.occam.data.problem_space.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.pb_space.ProblemSpaceExplorer;
import com.tregouet.occam.data.problem_space.IProblemSpace;
import com.tregouet.occam.data.problem_space.states.IRepresentation;
import com.tregouet.occam.data.problem_space.states.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.concepts.IContextObject;
import com.tregouet.occam.data.problem_space.states.concepts.IIsA;
import com.tregouet.occam.data.problem_space.transitions.AProblemStateTransition;

public class ProblemSpace implements IProblemSpace {

	private List<IContextObject> context;
	private final ProblemSpaceExplorer problemSpaceExplorer;
	private IRepresentation activeState = null;

	public ProblemSpace(Set<IContextObject> context) {
		this.context = new ArrayList<>(context);
		this.context.sort((x, y) -> Integer.compare(x.iD(), y.iD()));
		problemSpaceExplorer = IProblemSpace.problemSpaceExplorer().initialize(this.context);
	}

	@Override
	public DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> getProblemSpaceGraph() {
		return problemSpaceExplorer.getProblemSpaceGraph();
	}

	@Override
	public List<IContextObject> getContext() {
		return context;
	}

	@Override
	public Boolean display(int representationID) {
		if (activeState != null && activeState.iD() == representationID)
			return false;
		boolean representationFound = false;
		Iterator<IRepresentation> repIte = problemSpaceExplorer.getProblemSpaceGraph().vertexSet().iterator();
		while (!representationFound && repIte.hasNext()) {
			IRepresentation nextRep = repIte.next();
			if (nextRep.iD() == representationID) {
				activeState = nextRep;
				representationFound = true;
			}
		}
		if (!representationFound)
			return null;
		return true;
	}

	@Override
	public Boolean deepen(int representationID) {
		return problemSpaceExplorer.apply(representationID);
	}

	@Override
	public IRepresentation getActiveRepresentation() {
		return activeState;
	}

	@Override
	public DirectedAcyclicGraph<IConcept, IIsA> getLatticeOfConcepts() {
		return problemSpaceExplorer.getLatticeOfConcepts();
	}

}
