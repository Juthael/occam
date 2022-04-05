package com.tregouet.occam.alg.scorers.representations;

import com.tregouet.occam.alg.scorers.Scorer;
import com.tregouet.occam.data.logical_structures.scores.IScore;
import com.tregouet.occam.data.representations.IRepresentation;

public interface RepresentationScorer<S extends IScore<S>> extends Scorer<IRepresentation<S>, S> {

}
