package com.tregouet.occam.alg.builders.classifications;

import java.util.Map;
import java.util.function.BiFunction;

import com.tregouet.occam.data.structures.representations.classifications.IClassification;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IConcept;
import com.tregouet.occam.data.structures.representations.classifications.concepts.IIsA;
import com.tregouet.tree_finder.data.InvertedTree;

public interface ClassificationBuilder extends BiFunction<InvertedTree<IConcept, IIsA>, Map<Integer, IConcept>,
	IClassification> {

}
