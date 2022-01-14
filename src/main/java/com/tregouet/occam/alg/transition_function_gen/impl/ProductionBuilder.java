package com.tregouet.occam.alg.transition_function_gen.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.alg.transition_function_gen.IProductionBuilder;
import com.tregouet.occam.alg.transition_function_gen.utils.ProductionGenerator;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotationSets;
import com.tregouet.occam.data.languages.specific.IBasicProduction;
import com.tregouet.occam.data.languages.specific.IProduction;
import com.tregouet.occam.data.denotations.IDenotation;

public class ProductionBuilder implements IProductionBuilder {

	private final List<IDenotationSet> topologicalOrderOnDenotationSets;
	private final List<IBasicProduction> basicProductions = new ArrayList<>();
	private final List<IProduction> productions = new ArrayList<>();
	
	public ProductionBuilder(IDenotationSets denotationSets) {
		topologicalOrderOnDenotationSets = denotationSets.getTopologicalSorting();
		//build basics
		for (int i = 0 ; i < topologicalOrderOnDenotationSets.size() - 1 ; i++) {
			for (int j = i+1 ; j < topologicalOrderOnDenotationSets.size() ; j++) {
				for (IDenotation iDenotation : topologicalOrderOnDenotationSets.get(i).getDenotations()) {
					for (IDenotation jDenotation : topologicalOrderOnDenotationSets.get(j).getDenotations()) {
						List<IBasicProduction> ijDenotationsProds = 
								new ProductionGenerator(denotationSets, iDenotation, jDenotation).getProduction();
						if (ijDenotationsProds != null)
							basicProductions.addAll(ijDenotationsProds);
					}
				}
			}
		}
		//build composite if possible, otherwise add basic 
		for (IBasicProduction basicProduction : basicProductions) {
			IProduction compositeComponent = null;
			int prodIdx = 0;
			while (compositeComponent == null && prodIdx < productions.size()) {
				IProduction currentProd = productions.get(prodIdx); 
				compositeComponent = currentProd.compose(basicProduction);
				if (compositeComponent != null) {
					if (currentProd instanceof IBasicProduction) {
						productions.remove(prodIdx);
						productions.add(compositeComponent);
					}
				}
				prodIdx++;
			}
			if (compositeComponent == null)
				productions.add(basicProduction);
		}
	}

	@Override
	public List<IProduction> getProductions() {
		return productions;
	}

}
