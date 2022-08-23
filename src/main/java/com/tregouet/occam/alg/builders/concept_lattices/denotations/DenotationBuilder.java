package com.tregouet.occam.alg.builders.concept_lattices.denotations;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.data.structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IContextObject;

@FunctionalInterface
public interface DenotationBuilder extends Function<Collection<IContextObject>, Set<IConstruct>> {

}
