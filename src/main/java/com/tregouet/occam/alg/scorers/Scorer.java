package com.tregouet.occam.alg.scorers;

import java.util.function.Function;

import com.tregouet.occam.data.logical_structures.scores.IScore;

@FunctionalInterface
public interface Scorer<R extends Scored<S>, S extends IScore<S>> extends Function<R, S> {

}