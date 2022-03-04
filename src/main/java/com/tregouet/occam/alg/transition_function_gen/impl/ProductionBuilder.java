package com.tregouet.occam.alg.transition_function_gen.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.alg.transition_function_gen.IProductionBuilder;
import com.tregouet.occam.alg.transition_function_gen.utils.ProductionGenerator;
import com.tregouet.occam.data.denotations.IConcept;
import com.tregouet.occam.data.denotations.IConcepts;
import com.tregouet.occam.data.languages.specific.ISimpleEdgeProduction;
import com.tregouet.occam.data.languages.specific.IBasicProductionAsEdge;
import com.tregouet.occam.data.denotations.IDenotation;

public class ProductionBuilder implements IProductionBuilder {

	private final List<IConcept> topologicalOrderOnDenotationSets;
	private final List<ISimpleEdgeProduction> simpleEdgeProductions = new ArrayList<>();
	private final List<IBasicProductionAsEdge> basicProductionAsEdges = new ArrayList<>();
	
	public ProductionBuilder(IConcepts concepts) {
		topologicalOrderOnDenotationSets = concepts.getTopologicalSorting();
		//build basics
		for (int i = 0 ; i < topologicalOrderOnDenotationSets.size() - 1 ; i++) {
			for (int j = i+1 ; j < topologicalOrderOnDenotationSets.size() ; j++) {
				for (IDenotation iDenotation : topologicalOrderOnDenotationSets.get(i).getDenotations()) {
					for (IDenotation jDenotation : topologicalOrderOnDenotationSets.get(j).getDenotations()) {
						List<ISimpleEdgeProduction> ijDenotationsProds = 
								new ProductionGenerator(concepts, iDenotation, jDenotation).getProduction();
						if (ijDenotationsProds != null)
							simpleEdgeProductions.addAll(ijDenotationsProds);
					}
				}
			}
		}
		//build composite if possible, otherwise add basic 
		for (ISimpleEdgeProduction simpleEdgeProduction : simpleEdgeProductions) {
			IBasicProductionAsEdge compositeComponent = null;
			int prodIdx = 0;
			while (compositeComponent == null && prodIdx < basicProductionAsEdges.size()) {
				IBasicProductionAsEdge currentProd = basicProductionAsEdges.get(prodIdx); 
				compositeComponent = currentProd.combine(simpleEdgeProduction);
				if (compositeComponent != null) {
					if (currentProd instanceof ISimpleEdgeProduction) {
						basicProductionAsEdges.remove(prodIdx);
						basicProductionAsEdges.add(compositeComponent);
					}
				}
				prodIdx++;
			}
			if (compositeComponent == null)
				basicProductionAsEdges.add(simpleEdgeProduction);
		}
	}

	@Override
	public List<IBasicProductionAsEdge> getProductions() {
		return basicProductionAsEdges;
	}

}
