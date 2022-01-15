package com.tregouet.occam.alg.transition_function_gen.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.alg.transition_function_gen.IProductionBuilder;
import com.tregouet.occam.alg.transition_function_gen.utils.ProductionGenerator;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotationSets;
import com.tregouet.occam.data.languages.specific.ISimpleEdgeProduction;
import com.tregouet.occam.data.languages.specific.IEdgeProduction;
import com.tregouet.occam.data.denotations.IDenotation;

public class ProductionBuilder implements IProductionBuilder {

	private final List<IDenotationSet> topologicalOrderOnDenotationSets;
	private final List<ISimpleEdgeProduction> simpleEdgeProductions = new ArrayList<>();
	private final List<IEdgeProduction> edgeProductions = new ArrayList<>();
	
	public ProductionBuilder(IDenotationSets denotationSets) {
		topologicalOrderOnDenotationSets = denotationSets.getTopologicalSorting();
		//build basics
		for (int i = 0 ; i < topologicalOrderOnDenotationSets.size() - 1 ; i++) {
			for (int j = i+1 ; j < topologicalOrderOnDenotationSets.size() ; j++) {
				for (IDenotation iDenotation : topologicalOrderOnDenotationSets.get(i).getDenotations()) {
					for (IDenotation jDenotation : topologicalOrderOnDenotationSets.get(j).getDenotations()) {
						List<ISimpleEdgeProduction> ijDenotationsProds = 
								new ProductionGenerator(denotationSets, iDenotation, jDenotation).getProduction();
						if (ijDenotationsProds != null)
							simpleEdgeProductions.addAll(ijDenotationsProds);
					}
				}
			}
		}
		//build composite if possible, otherwise add basic 
		for (ISimpleEdgeProduction simpleEdgeProduction : simpleEdgeProductions) {
			IEdgeProduction compositeComponent = null;
			int prodIdx = 0;
			while (compositeComponent == null && prodIdx < edgeProductions.size()) {
				IEdgeProduction currentProd = edgeProductions.get(prodIdx); 
				compositeComponent = currentProd.combine(simpleEdgeProduction);
				if (compositeComponent != null) {
					if (currentProd instanceof ISimpleEdgeProduction) {
						edgeProductions.remove(prodIdx);
						edgeProductions.add(compositeComponent);
					}
				}
				prodIdx++;
			}
			if (compositeComponent == null)
				edgeProductions.add(simpleEdgeProduction);
		}
	}

	@Override
	public List<IEdgeProduction> getProductions() {
		return edgeProductions;
	}

}
