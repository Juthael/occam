package com.tregouet.occam.data.modules.sorting;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.BuildersAbstractFactory;
import com.tregouet.occam.alg.builders.categorizer.ProblemSpaceExplorer;
import com.tregouet.occam.data.modules.IModule;
import com.tregouet.occam.data.modules.sorting.transitions.AProblemStateTransition;
import com.tregouet.occam.data.structures.representations.IRepresentation;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;

public interface ISorter extends IModule {

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

	DirectedAcyclicGraph<IRepresentation, AProblemStateTransition> getProblemSpaceGraph();

	@Override
	ISorter process(Collection<IContextObject> context);
	Boolean restrictTo(Set<Integer> representationIDs);

	public static ProblemSpaceExplorer problemSpaceExplorer() {
		return BuildersAbstractFactory.INSTANCE.getProblemSpaceExplorer();
	}

}
