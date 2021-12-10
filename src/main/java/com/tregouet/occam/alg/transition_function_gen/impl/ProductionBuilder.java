package com.tregouet.occam.alg.transition_function_gen.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.alg.transition_function_gen.IProductionBuilder;
import com.tregouet.occam.alg.transition_function_gen.utils.ProductionGenerator;
import com.tregouet.occam.data.abstract_machines.transitions.IBasicProduction;
import com.tregouet.occam.data.abstract_machines.transitions.IProduction;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IIntentAttribute;

public class ProductionBuilder implements IProductionBuilder {

	private final List<IConcept> topologicalOrderOnCats;
	private final List<IBasicProduction> basicProductions = new ArrayList<>();
	private final List<IProduction> productions = new ArrayList<>();
	
	public ProductionBuilder(IConcepts concepts) {
		topologicalOrderOnCats = concepts.getTopologicalSorting();
		//build basics
		for (int i = 0 ; i < topologicalOrderOnCats.size() - 1 ; i++) {
			for (int j = i+1 ; j < topologicalOrderOnCats.size() ; j++) {
				//HERE optimisable
				for (IIntentAttribute iCatAtt : topologicalOrderOnCats.get(i).getIntent()) {
					for (IIntentAttribute jCatAtt : topologicalOrderOnCats.get(j).getIntent()) {
						List<IBasicProduction> ijAttProds = 
								new ProductionGenerator(concepts, iCatAtt, jCatAtt).getProduction();
						if (ijAttProds != null)
							basicProductions.addAll(ijAttProds);
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
