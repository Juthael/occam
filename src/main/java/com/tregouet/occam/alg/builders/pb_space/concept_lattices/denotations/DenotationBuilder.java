package com.tregouet.occam.alg.builders.pb_space.concept_lattices.denotations;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IContextObject;

@FunctionalInterface
public interface DenotationBuilder extends Function<Collection<IContextObject>, Set<IConstruct>> {

}
