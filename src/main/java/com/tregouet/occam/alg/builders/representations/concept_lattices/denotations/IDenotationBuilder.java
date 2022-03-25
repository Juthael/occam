package com.tregouet.occam.alg.builders.representations.concept_lattices.denotations;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.data.languages.words.construct.IConstruct;
import com.tregouet.occam.data.representations.concepts.IContextObject;

@FunctionalInterface
public interface IDenotationBuilder extends Function<Collection<IContextObject>, Set<IConstruct>> {	

}
