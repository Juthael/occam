package com.tregouet.occam.alg.scorers.similarity;

import com.tregouet.occam.alg.scorers.Scored;
import com.tregouet.occam.alg.scorers.Scorer;
import com.tregouet.occam.alg.scorers.scores.DoubleScore;
import com.tregouet.occam.data.representations.descriptions.IDescription;

public interface SimilarityScorer<R extends Scored<DoubleScore>> extends Scorer<R, DoubleScore> {
	
	Scorer<R, DoubleScore> setAsContext(IDescription description);

}
