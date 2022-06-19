package com.tregouet.occam.alg.scorers.similarity.relative;

import com.tregouet.occam.alg.scorers.Scored;
import com.tregouet.occam.alg.scorers.Scorer;
import com.tregouet.occam.data.logical_structures.scores.impl.DoubleScore;
import com.tregouet.occam.data.problem_space.states.descriptions.differentiae.ADifferentiae;
import com.tregouet.tree_finder.data.Tree;

public interface RelativeSimilarityScorer<R extends Scored<DoubleScore>> extends Scorer<R, DoubleScore> {

	RelativeSimilarityScorer<R> setAsContext(Tree<Integer, ADifferentiae> classificationTree);

}
