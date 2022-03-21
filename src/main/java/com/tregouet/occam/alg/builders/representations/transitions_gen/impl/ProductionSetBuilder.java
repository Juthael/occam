package com.tregouet.occam.alg.builders.representations.transitions_gen.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.representations.transitions_gen.IProductionSetBuilder;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.preconcepts.IPreconcepts;

public class ProductionSetBuilder implements IProductionSetBuilder<IContextualizedProduction> {

	public static final ProductionSetBuilder INSTANCE = new ProductionSetBuilder();
	
	private Set<IContextualizedProduction> productions = null;
	
	private ProductionSetBuilder() {
	}

	@Override
	public IProductionSetBuilder<IContextualizedProduction> input(IPreconcepts preconcepts) {
		List<IPreconcept> topoOrderedConcepts = preconcepts.getTopologicalSorting();
		productions = new HashSet<>();
		for (int i = 0 ; i < topoOrderedConcepts.size() - 1 ; i++) {
			IPreconcept iConcept = topoOrderedConcepts.get(i);
			for (int j = i+1 ; j < topoOrderedConcepts.size() ; j++) {
				IPreconcept jConcept = topoOrderedConcepts.get(j);
				if (preconcepts.isA(iConcept, jConcept)) {
					for (IDenotation source : iConcept.getDenotations()) {
						for (IDenotation target : jConcept.getDenotations()) {
							Set<IContextualizedProduction> ijDenotationsProds = 
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
	public Set<IContextualizedProduction> output() {
		return productions;
	}

}
