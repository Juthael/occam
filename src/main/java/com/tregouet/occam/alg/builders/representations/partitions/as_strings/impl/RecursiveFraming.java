package com.tregouet.occam.alg.builders.representations.partitions.as_strings.impl;

import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.representations.partitions.as_strings.PartitionStringBuilder;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public class RecursiveFraming implements PartitionStringBuilder {
	
	private DirectedAcyclicGraph<Integer, AbstractDifferentiae> partitionGraph = null;
	private Map<Integer, List<Integer>> conceptID2ExtentIDs = null;
	
	public RecursiveFraming() {
	}

	@Override
	public String apply(Tree<Integer, AbstractDifferentiae> partitionGraph) {
		this.partitionGraph = partitionGraph;
		return doFrame(partitionGraph.getRoot());
	}
	
	private String doFrame(Integer frameConceptID) {
		if (partitionGraph.outDegreeOf(frameConceptID) == 0) {
			return "(" + getConceptExtensionAsString(frameConceptID) + ")";
		}
		else {
			StringBuilder sB = new StringBuilder();
			sB.append("(");
			Integer[] orderedSubConcepts = 
					IPartition.orderOverIDs(Graphs.successorListOf(partitionGraph, frameConceptID));
			for (Integer subconcept : orderedSubConcepts)
				sB.append(doFrame(subconcept));
			sB.append(")");
			return sB.toString();
		}
	}
	
	private String getConceptExtensionAsString(Integer conceptID) {
		List<Integer> extentIDs = conceptID2ExtentIDs.get(conceptID);
		int extentSize = extentIDs.size();
		if (extentSize == 1)
			return extentIDs.get(0).toString();
		StringBuilder sB = new StringBuilder();
		for (int i = 0 ; i < extentSize ; i++) {
			sB.append(extentIDs.get(i));
			if (i < extentSize - 1)
				sB.append(", ");
		}
		return sB.toString();
	}

	@Override
	public PartitionStringBuilder setUp(Map<Integer, List<Integer>> conceptID2ExtentIDs) {
		this.conceptID2ExtentIDs = conceptID2ExtentIDs;
		return this;
	}

}
