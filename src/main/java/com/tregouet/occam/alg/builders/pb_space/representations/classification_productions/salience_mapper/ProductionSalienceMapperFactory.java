package com.tregouet.occam.alg.builders.pb_space.representations.classification_productions.salience_mapper;

import com.tregouet.occam.alg.builders.pb_space.representations.classification_productions.salience_mapper.impl.HiddenByDefaultThenFindSpecifics;

public class ProductionSalienceMapperFactory {
	
	public static final ProductionSalienceMapperFactory INSTANCE = new ProductionSalienceMapperFactory();
	
	private ProductionSalienceMapperFactory() {
	}
	
	public ProductionSalienceMapper apply(ProductionSalienceMapperStrategy strategy) {
		switch (strategy) {
		case HIDDEN_THEN_FIND_SPECIFICS : 
				return new HiddenByDefaultThenFindSpecifics();
		default : 
			return null;
		}
	}

}
