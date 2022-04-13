package com.tregouet.occam.alg.builders.representations.partitions.as_strings.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.representations.partitions.as_strings.PartitionStringBuilder;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;
import com.tregouet.occam.data.representations.concepts.ConceptType;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IIsA;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.InvertedTree;
import com.tregouet.tree_finder.utils.Functions;

public class RecursiveFraming implements PartitionStringBuilder {
	
	private DirectedAcyclicGraph<IConcept, IIsA> concepts = null;
	private SortedSet<Integer> particularIDs = new TreeSet<>();
	private DirectedAcyclicGraph<Integer, AbstractDifferentiae> partitionGraph = null;
	
	public RecursiveFraming() {
	}

	@Override
	public String apply(DirectedAcyclicGraph<IConcept, IIsA> concepts, DirectedAcyclicGraph<Integer, AbstractDifferentiae> partitionGraph) {
		this.concepts = concepts;
		this.partitionGraph = partitionGraph;
		Collection<IConcept> leaves = (concepts instanceof InvertedTree<?, ?> ? 
				((InvertedTree<IConcept, IIsA>) concepts).getLeaves() : getLeaves(concepts));
		for (IConcept particular : leaves)
			particularIDs.add(particular.iD());
		return doFrame(getTruism().iD());
	}
	
	private String doFrame(Integer frameConceptID) {
		if (partitionGraph.outDegreeOf(frameConceptID) == 0) {
			return "(" + getConceptExtensionAsString(frameConceptID) + ")";
		}
		else {
			StringBuilder sB = new StringBuilder();
			sB.append("(");
			Integer[] orderedSubConcepts = IPartition.orderOverIDs(Graphs.successorListOf(partitionGraph, frameConceptID));
			for (Integer subconcept : orderedSubConcepts)
				sB.append(doFrame(subconcept));
			sB.append(")");
			return sB.toString();
		}
	}
	
	private String getConceptExtensionAsString(Integer conceptID) {
		if (particularIDs.contains(conceptID))
			return conceptID.toString();
		else {
			Set<IConcept> lowerSet = Functions.lowerSet(concepts, getConceptWithID(conceptID));
			SortedSet<Integer> extensionIDs = new TreeSet<>();
			for (IConcept lowerBound : lowerSet)
				extensionIDs.add(lowerBound.iD());
			extensionIDs.retainAll(particularIDs);
			StringBuilder sB = new StringBuilder();
			Iterator<Integer> extensionIte = extensionIDs.iterator();
			while (extensionIte.hasNext()) {
				sB.append(extensionIte.next().toString());
				if (extensionIte.hasNext())
					sB.append(" ");
			}
			return extensionIte.toString();
		}
	}
	
	private IConcept getConceptWithID(Integer iD) {
		Iterator<IConcept> topoIte = concepts.iterator();
		while (topoIte.hasNext()) {
			IConcept nextConcept = topoIte.next();
			if (nextConcept.iD() == iD)
				return nextConcept;
		}
		return null;
	}
	
	private IConcept getTruism() {
		for (IConcept concept : concepts) {
			if (concept.type() == ConceptType.TRUISM)
				return concept;
		}
		return null;
	}
	
	private List<IConcept> getLeaves(DirectedAcyclicGraph<IConcept, IIsA> concepts) {
		List<IConcept> leaves = new ArrayList<>();
		for (IConcept concept : concepts) {
			if (concepts.inDegreeOf(concept) == 0)
				leaves.add(concept);
		}
		return leaves;
	}

}
