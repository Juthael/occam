package com.tregouet.occam.alg.calculators.costs.productions.impl;

import com.google.common.base.Function;
import com.tregouet.occam.alg.calculators.costs.productions.IProductionCoster;
import com.tregouet.occam.alg.calculators.costs.productions.ProductionCostingStrategy;

public class ProductionCosterFactory implements
		Function<ProductionCostingStrategy, IProductionCoster> {

	public static final ProductionCosterFactory INSTANCE = new ProductionCosterFactory();
	
	private ProductionCosterFactory() {
	}

	@Override
	public IProductionCoster apply(ProductionCostingStrategy strategy) {
		switch (strategy) {
			default : 
				return null;
		}
	}

}
