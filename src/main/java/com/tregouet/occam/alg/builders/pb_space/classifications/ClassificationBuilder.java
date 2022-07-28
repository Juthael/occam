package com.tregouet.occam.alg.builders.pb_space.classifications;

import java.util.Map;
import java.util.function.BiFunction;

import com.tregouet.occam.data.problem_space.states.classifications.IClassification;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedTree;

public interface ClassificationBuilder extends BiFunction<InvertedTree<IConcept, IIsA>, Map<Integer, IConcept>, IClassification> {

}
