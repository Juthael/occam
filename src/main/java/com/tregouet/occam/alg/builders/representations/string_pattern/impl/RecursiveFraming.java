package com.tregouet.occam.alg.builders.representations.string_pattern.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DirectedAcyclicGraph;

import com.tregouet.occam.alg.builders.representations.string_pattern.StringPatternBuilder;
import com.tregouet.occam.data.problem_spaces.partitions.IPartition;
import com.tregouet.occam.data.representations.descriptions.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public class RecursiveFraming implements StringPatternBuilder {

	private DirectedAcyclicGraph<Integer, AbstractDifferentiae> partitionGraph = null;
	private Map<Integer, List<Integer>> conceptID2ExtentIDs = null;

	public RecursiveFraming() {
	}

	@Override
	public String apply(Tree<Integer, AbstractDifferentiae> partitionGraph) {
		this.partitionGraph = partitionGraph;
		return improveReadability(doFrame(getMaximalNonTrivialElement(partitionGraph)));
	}

	@Override
	public StringPatternBuilder setUp(Map<Integer, List<Integer>> conceptID2ExtentIDs) {
		this.conceptID2ExtentIDs = conceptID2ExtentIDs;
		return this;
	}

	private String doFrame(Integer frameConceptID) {
		if (partitionGraph.outDegreeOf(frameConceptID) == 0) {
			return "(" + getConceptExtensionAsString(frameConceptID) + ")";
		} else {
			StringBuilder sB = new StringBuilder();
			sB.append("(");
			Integer[] orderedSubConcepts = IPartition
					.orderOverIDs(Graphs.successorListOf(partitionGraph, frameConceptID));
			for (Integer subconcept : orderedSubConcepts)
				sB.append(doFrame(subconcept));
			sB.append(")");
			return sB.toString();
		}
	}

	private String getConceptExtensionAsString(Integer conceptID) {
		if (conceptID2ExtentIDs == null)
			// then the arg given to apply() must be the graph of a complete representation
			return conceptID.toString();
		List<Integer> extentIDs = conceptID2ExtentIDs.get(conceptID);
		int extentSize = extentIDs.size();
		if (extentSize == 1)
			return extentIDs.get(0).toString();
		StringBuilder sB = new StringBuilder();
		for (int i = 0; i < extentSize; i++) {
			sB.append(extentIDs.get(i));
			if (i < extentSize - 1)
				sB.append(", ");
		}
		return sB.toString();
	}
	
	private Integer getMaximalNonTrivialElement(Tree<Integer, AbstractDifferentiae> partitionGraph) {
		Integer maxNonTrivial = partitionGraph.getRoot();
		while (partitionGraph.outDegreeOf(maxNonTrivial) == 1) {
			maxNonTrivial = partitionGraph.outgoingEdgesOf(maxNonTrivial).iterator().next().getTarget();
		}
		return maxNonTrivial;
	}
	
	private String improveReadability(String pattern) {
		String[] patternArray = toArray(pattern);
		int[] openingDegree = new int[patternArray.length];
		int[] closingDegree = new int[patternArray.length];
		int degreeCount = 0;
		for (int i = 0 ; i < patternArray.length ; i++) {
			if (patternArray[i].equals("(")) {
				degreeCount++;
				openingDegree[i] = degreeCount;
			}
			else if (patternArray[i].equals(")")) {
				closingDegree[i] = degreeCount;
				degreeCount--;
			}
		}
		StringBuilder sB = new StringBuilder();
		for (int i = 0 ; i < patternArray.length ; i++) {
			sB.append(convert(patternArray[i], openingDegree[i], closingDegree[i]));
		}
		return sB.toString();
	}
	
	private String convert(String s, int openingDegree, int closingDegree) {
		if (openingDegree > 0)
			return getBracket(openingDegree, true);
		else if (closingDegree > 0)
			return getBracket(closingDegree, false);
		else return s;
	}
	
	private String getBracket(int degree, boolean opening) {
		switch (degree) {
		case 0 : 
		case 1 : 
			return new String();			
		case 2 : 
			if (opening)
				return "{";
			else return "}";
		case 3 : 
			if (opening)
				return "[";
			else return "]";
		case 4 : 
			if (opening)
				return "(";
			else return ")";
		case 5 : 
			if (opening)
				return "<";
			else return ">";			
		default : 
			if (opening)
				return "'";
			else return "'";
		}
	}
	
	private String[] toArray(String pattern) {
		List<String> stringList = new ArrayList<>();
		char[] array = pattern.toCharArray();
		StringBuilder sB = new StringBuilder();
		for (int i = 0 ; i < array.length ; i++) {
			char iChar = array[i];
			if (iChar == '(' || iChar == ')') {
				if (!sB.isEmpty()) {
					stringList.add(sB.toString());
					sB.setLength(0);
				}
				stringList.add(Character.toString(iChar));
			}
			else sB.append(iChar);
		}
		return stringList.toArray(new String[stringList.size()]);
	}

}
