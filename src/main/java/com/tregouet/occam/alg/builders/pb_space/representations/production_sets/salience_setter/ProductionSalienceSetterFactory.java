package com.tregouet.occam.alg.builders.pb_space.representations.production_sets.salience_setter;

import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.salience_setter.impl.EverythingIsRule;
import com.tregouet.occam.alg.builders.pb_space.representations.production_sets.salience_setter.impl.HiddenByDefaultThenFindSpecifics;

public class ProductionSalienceSetterFactory {

	public static final ProductionSalienceSetterFactory INSTANCE = new ProductionSalienceSetterFactory();

	private ProductionSalienceSetterFactory() {
	}

	public ProductionSalienceSetter apply(ProductionSalienceSetterStrategy strategy) {
		switch (strategy) {
		case HIDDEN_THEN_FIND_SPECIFICS :
				return new HiddenByDefaultThenFindSpecifics();
		case EVERYTHING_IS_RULE :
			return EverythingIsRule.INSTANCE;
		default :
			return null;
		}
	}

}
