package com.tregouet.occam.alg.scorers.similarity.relative;

import com.tregouet.occam.alg.scorers.Scored;
import com.tregouet.occam.alg.scorers.Scorer;
import com.tregouet.occam.data.logical_structures.scores.impl.IDoubleScore;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface RelativeSimilarityScorer<R extends Scored<IDoubleScore>> extends Scorer<R, IDoubleScore> {

	RelativeSimilarityScorer<R> setAsContext(Tree<Integer, ADifferentiae> classificationTree);

}
