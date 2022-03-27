package com.tregouet.occam.alg.scorers;

import java.util.function.Function;

@FunctionalInterface
public interface Scorer<R extends Scored<S>, S extends Score<S>> extends Function<R, S> {

}
