package com.tregouet.occam.alg.builders.pb_space.concepts_trees;

import com.tregouet.occam.alg.builders.pb_space.concepts_trees.impl.IfLeafIsUniversalThenSort;
import com.tregouet.occam.alg.builders.pb_space.concepts_trees.impl.NoSize2Leaf;

public class ConceptTreeGrowerFactory {

	public static final ConceptTreeGrowerFactory INSTANCE = new ConceptTreeGrowerFactory();

	private ConceptTreeGrowerFactory() {
	}

	public ConceptTreeGrower apply(ConceptTreeGrowerStrategy strategy) {
		switch(strategy) {
		case IF_LEAF_IS_UNIVERSAL_THEN_SORT :
			return new IfLeafIsUniversalThenSort();
		case NO_SIZE_2_LEAF :
			return new NoSize2Leaf();
		default :
			return null;
		}
	}

}
