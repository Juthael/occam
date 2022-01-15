package com.tregouet.occam.alg.transition_function_gen.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.alg.transition_function_gen.IProductionBuilder;
import com.tregouet.occam.alg.transition_function_gen.utils.ProductionGenerator;
import com.tregouet.occam.data.denotations.IDenotationSet;
import com.tregouet.occam.data.denotations.IDenotationSets;
import com.tregouet.occam.data.languages.specific.ISimpleEdgeProduction;
import com.tregouet.occam.data.languages.specific.IProductionAsEdge;
import com.tregouet.occam.data.denotations.IDenotation;

public class ProductionBuilder implements IProductionBuilder {

	private final List<IDenotationSet> topologicalOrderOnDenotationSets;
	private final List<ISimpleEdgeProduction> simpleEdgeProductions = new ArrayList<>();
	private final List<IProductionAsEdge> productionAsEdges = new ArrayList<>();
	
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
			IProductionAsEdge compositeComponent = null;
			int prodIdx = 0;
			while (compositeComponent == null && prodIdx < productionAsEdges.size()) {
				IProductionAsEdge currentProd = productionAsEdges.get(prodIdx); 
				compositeComponent = currentProd.combine(simpleEdgeProduction);
				if (compositeComponent != null) {
					if (currentProd instanceof ISimpleEdgeProduction) {
						productionAsEdges.remove(prodIdx);
						productionAsEdges.add(compositeComponent);
					}
				}
				prodIdx++;
			}
			if (compositeComponent == null)
				productionAsEdges.add(simpleEdgeProduction);
		}
	}

	@Override
	public List<IProductionAsEdge> getProductions() {
		return productionAsEdges;
	}

}
