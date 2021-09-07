package com.tregouet.occam.transition_function.impl;

import java.util.List;

import org.jgrapht.graph.DefaultEdge;

import com.tregouet.occam.data.categories.ICategory;
import com.tregouet.occam.data.operators.IOperator;
import com.tregouet.occam.transition_function.ISimilarityCalculator;
import com.tregouet.tree_finder.data.InTree;

public class SimilarityCalculator implements ISimilarityCalculator {

	public SimilarityCalculator(int nbOfObjects, InTree<ICategory, DefaultEdge> categories, List<IOperator> operators) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getCoherenceScore() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getCoherenceScore(List<Integer> objectIndexes) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double howSimilar(int onjIdx1, int objIndex2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double howSimilarTo(int objIdx1, int objIdx2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double howProtoypical(int objIdx1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double howPrototypicalAmong(int objIdx, List<Integer> objSubset) {
		// TODO Auto-generated method stub
		return 0;
	}

}
