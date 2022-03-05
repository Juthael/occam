package com.tregouet.occam.alg.transition_function_gen.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.alg.transition_function_gen.IProductionBuilder;
import com.tregouet.occam.alg.transition_function_gen.utils.ProductionGenerator;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IDenotation;
import com.tregouet.occam.data.languages.specific.IBasicProduction;
import com.tregouet.occam.data.languages.specific.IStronglyContextualized;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.IntIntImmutablePair;

public class ProductionBuilder implements IProductionBuilder {

	private final List<IConcept> topologicalOrderOnConcepts;
	private final Map<Pair<Integer, Integer>, List<IBasicProduction>> conceptIndexesToProds = new HashMap<>();
	private final List<IStronglyContextualized> stronglyContextualizeds = new ArrayList<>();
	
	public ProductionBuilder(IConcepts concepts) {
		topologicalOrderOnConcepts = concepts.getTopologicalSorting();
		//build basics
		for (int i = 0 ; i < topologicalOrderOnConcepts.size() - 1 ; i++) {
			IConcept iConcept = topologicalOrderOnConcepts.get(i);
			for (int j = i+1 ; j < topologicalOrderOnConcepts.size() ; j++) {
				IConcept jConcept = topologicalOrderOnConcepts.get(j);
				if (concepts.isA(iConcept, jConcept)) {
					Pair ijIndexes = new IntIntImmutablePair(i, j);
					List<IBasicProduction> ijBasicProductions = new ArrayList<>();
					for (IDenotation iDenotation : topologicalOrderOnConcepts.get(i).getDenotations()) {
						for (IDenotation jDenotation : topologicalOrderOnConcepts.get(j).getDenotations()) {
							List<IBasicProduction> ijDenotationsProds = 
									new ProductionGenerator(iDenotation, jDenotation).getProduction();
							if (ijDenotationsProds != null)
								basicProductions.addAll(ijDenotationsProds);
						}
					}
				}
			}
		}
		//build composite if possible, otherwise add basic 
		for (IBasicProduction basicProduction : basicProductions) {
			IStronglyContextualized compositeComponent = null;
			int prodIdx = 0;
			while (compositeComponent == null && prodIdx < stronglyContextualizeds.size()) {
				IStronglyContextualized currentProd = stronglyContextualizeds.get(prodIdx); 
				compositeComponent = null ;//currentProd.combine(simpleEdgeProduction);
				if (compositeComponent != null) {
					if (currentProd instanceof IBasicProduction) {
						stronglyContextualizeds.remove(prodIdx);
						stronglyContextualizeds.add(compositeComponent);
					}
				}
				prodIdx++;
			}
			if (compositeComponent == null)
				stronglyContextualizeds.add(basicProduction);
		}
	}

	@Override
	public List<IStronglyContextualized> getProductions() {
		return stronglyContextualizeds;
	}

}
