package com.tregouet.occam.alg.transition_function_gen.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.alg.transition_function_gen.IProductionSetBuilder;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.concepts.IConcept;
import com.tregouet.occam.data.concepts.IConcepts;
import com.tregouet.occam.data.concepts.IDenotation;

public class ProductionSetBuilder implements IProductionSetBuilder<IContextualizedProduction> {

	public static final ProductionSetBuilder INSTANCE = new ProductionSetBuilder();
	
	private List<IContextualizedProduction> productions = null;
	
	private ProductionSetBuilder() {
	}

	@Override
	public IProductionSetBuilder<IContextualizedProduction> input(IConcepts concepts) {
		List<IConcept> topoOrderedConcepts = concepts.getTopologicalSorting();
		productions = new ArrayList<>();
		for (int i = 0 ; i < topoOrderedConcepts.size() - 1 ; i++) {
			IConcept iConcept = topoOrderedConcepts.get(i);
			for (int j = i+1 ; j < topoOrderedConcepts.size() ; j++) {
				IConcept jConcept = topoOrderedConcepts.get(j);
				if (concepts.isA(iConcept, jConcept)) {
					for (IDenotation source : iConcept.getDenotations()) {
						for (IDenotation target : jConcept.getDenotations()) {
							List<IContextualizedProduction> ijDenotationsProds = 
									ContextualizedProductionBuilder.INSTANCE.input(source, target).output();
							productions.addAll(ijDenotationsProds);
						}
					}
				}
			}
		}
		return this;
	}

	@Override
	public List<IContextualizedProduction> output() {
		return productions;
	}

}
