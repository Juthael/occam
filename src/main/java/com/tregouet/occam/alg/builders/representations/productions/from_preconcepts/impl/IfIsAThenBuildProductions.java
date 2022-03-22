package com.tregouet.occam.alg.builders.representations.productions.from_preconcepts.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.representations.productions.from_preconcepts.IProdBuilderFromPreconceptLattice;
import com.tregouet.occam.data.alphabets.productions.IContextualizedProduction;
import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.preconcepts.IPreconcept;
import com.tregouet.occam.data.preconcepts.IPreconceptLattice;

public class IfIsAThenBuildProductions implements IProdBuilderFromPreconceptLattice {

	public static final IfIsAThenBuildProductions INSTANCE = new IfIsAThenBuildProductions();
	
	private Set<IContextualizedProduction> productions = null;
	
	public IfIsAThenBuildProductions() {
	}

	@Override
	public IProdBuilderFromPreconceptLattice input(IPreconceptLattice preconceptLattice) {
		List<IPreconcept> topoOrderedConcepts = preconceptLattice.getTopologicalSorting();
		productions = new HashSet<>();
		for (int i = 0 ; i < topoOrderedConcepts.size() - 1 ; i++) {
			IPreconcept iConcept = topoOrderedConcepts.get(i);
			for (int j = i+1 ; j < topoOrderedConcepts.size() ; j++) {
				IPreconcept jConcept = topoOrderedConcepts.get(j);
				if (preconceptLattice.isA(iConcept, jConcept)) {
					for (IDenotation source : iConcept.getDenotations()) {
						for (IDenotation target : jConcept.getDenotations()) {
							Set<IContextualizedProduction> ijDenotationsProds = 
									GeneratorsAbstractFactory.INSTANCE.getProdBuilderFromDenotations()
										.input(source, target)
										.output();
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
