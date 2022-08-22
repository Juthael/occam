package com.tregouet.occam.alg.builders.pb_space.concept_lattices.denotations;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.data.representations.classifications.concepts.IContextObject;
import com.tregouet.occam.data.structures.languages.words.construct.IConstruct;

@FunctionalInterface
public interface DenotationBuilder extends Function<Collection<IContextObject>, Set<IConstruct>> {

}
