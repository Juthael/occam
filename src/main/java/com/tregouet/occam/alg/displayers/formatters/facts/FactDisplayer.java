package com.tregouet.occam.alg.displayers.formatters.facts;

import java.util.Set;
import java.util.function.Function;

import com.tregouet.occam.data.representations.evaluation.facts.IFact;

public interface FactDisplayer extends Function<Set<IFact>, String> {

}
