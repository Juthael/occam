package com.tregouet.occam.alg.transition_function_gen.impl;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.alg.transition_function_gen.IProductionSetBuilder;
import com.tregouet.occam.data.alphabets.productions.Input;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.preconcepts.IPreconcepts;

public class ProductionSetBuilder implements IProductionSetBuilder<Input> {

	public static final ProductionSetBuilder INSTANCE = new ProductionSetBuilder();
	
	private List<Input> productions = null;
	
	private ProductionSetBuilder() {
	}

	@Override
	public IProductionSetBuilder<Input> input(IPreconcepts preconcepts) {
		List<IPreconcept> topoOrderedConcepts = preconcepts.getTopologicalSorting();
		productions = new ArrayList<>();
		for (int i = 0 ; i < topoOrderedConcepts.size() - 1 ; i++) {
			IPreconcept iConcept = topoOrderedConcepts.get(i);
			for (int j = i+1 ; j < topoOrderedConcepts.size() ; j++) {
				IPreconcept jConcept = topoOrderedConcepts.get(j);
				if (preconcepts.isA(iConcept, jConcept)) {
					for (IDenotation source : iConcept.getDenotations()) {
						for (IDenotation target : jConcept.getDenotations()) {
							List<Input> ijDenotationsProds = 
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
	public List<Input> output() {
		return productions;
	}

}
