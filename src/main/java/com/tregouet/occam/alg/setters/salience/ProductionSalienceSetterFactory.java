package com.tregouet.occam.alg.setters.salience;

import com.tregouet.occam.alg.setters.salience.impl.EverythingIsRule;
import com.tregouet.occam.alg.setters.salience.impl.HiddenByDefaultThenFindSpecifics;

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
