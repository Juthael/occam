package com.tregouet.occam.alg.builders.pb_space.representations.lattice_productions.from_denotations;

import com.tregouet.occam.alg.builders.pb_space.representations.lattice_productions.from_denotations.impl.MapTargetVarsToSourceValues;
import com.tregouet.occam.alg.builders.pb_space.representations.lattice_productions.from_denotations.impl.SourceConceptCannotHaveTargetDenotation;

public class ProdBldrFromDenotationsFactory {

	public static final ProdBldrFromDenotationsFactory INSTANCE = new ProdBldrFromDenotationsFactory();

	private ProdBldrFromDenotationsFactory() {
	}

	public ProdBuilderFromDenotations apply(ProdBuilderFromDenotationsStrategy strategy) {
		switch (strategy) {
		case MAP_TARGET_VARS_TO_SOURCE_VALUES:
			return new MapTargetVarsToSourceValues();
		case SRCE_CNCPT_CANNOT_HAVE_TGET_DENOT:
			return new SourceConceptCannotHaveTargetDenotation();
		default:
			return null;
		}
	}

}
