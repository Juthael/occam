package com.tregouet.occam.alg.scorers.similarity;

import com.tregouet.occam.data.representations.descriptions.IDescription;
import com.tregouet.occam.data.representations.descriptions.subsets.IConceptSubsetIDs;

public interface SubsetSimilarityScorer extends SimilarityScorer<IConceptSubsetIDs> {
	
	SubsetSimilarityScorer setAsContext(IDescription description);

}
