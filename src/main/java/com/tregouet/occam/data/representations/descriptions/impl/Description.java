package com.tregouet.occam.data.representations.descriptions.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.properties.AbstractDifferentiae;
import com.tregouet.tree_finder.data.Tree;

public class Description implements IDescription {

	private final Tree<Integer, AbstractDifferentiae> classification;
	private final List<Integer> topoOrderedConceptIDs;
	private final double[][] similarityMatrix;
	private final double[][] asymmetricalSimilarityMatrix;
	
	public Description(
			Tree<Integer, AbstractDifferentiae> classification, 
			List<Integer> topoOrderedConceptIDS, 
			double[][]similarityMatrix, 
			double[][]asymetricalSimilarityMatrix) {
		this.classification = classification;
		this.topoOrderedConceptIDs = topoOrderedConceptIDS;
		this.similarityMatrix = similarityMatrix;
		this.asymmetricalSimilarityMatrix = asymetricalSimilarityMatrix;
	}
	
	
	@Override
	public Tree<Integer, AbstractDifferentiae> asGraph() {
		return classification;
	}

	@Override
	public double[][] getSimilarityMatrix() {
		return similarityMatrix;
	}

	@Override
	public double[][] getAsymmetricalSimilarityMatrix() {
		return asymmetricalSimilarityMatrix;
	}


	@Override
	public List<Integer> getTopologicallyOrderedConceptIDs() {
		return new ArrayList<>(topoOrderedConceptIDs);
	}

}
