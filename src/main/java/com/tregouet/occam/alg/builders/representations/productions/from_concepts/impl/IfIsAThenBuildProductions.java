package com.tregouet.occam.alg.builders.representations.productions.from_concepts.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.GeneratorsAbstractFactory;
import com.tregouet.occam.alg.builders.representations.productions.from_concepts.IProdBuilderFromConceptLattice;
import com.tregouet.occam.data.languages.alphabets.domain_specific.IContextualizedProduction;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.IDenotation;

public class IfIsAThenBuildProductions implements IProdBuilderFromConceptLattice {

	public static final IfIsAThenBuildProductions INSTANCE = new IfIsAThenBuildProductions();
	
	private Set<IContextualizedProduction> productions = null;
	
	public IfIsAThenBuildProductions() {
	}

	@Override
	public IProdBuilderFromConceptLattice input(IConceptLattice conceptLattice) {
		List<IConcept> topoOrderedConcepts = conceptLattice.getTopologicalSorting();
		productions = new HashSet<>();
		for (int i = 0 ; i < topoOrderedConcepts.size() - 1 ; i++) {
			IConcept iConcept = topoOrderedConcepts.get(i);
			for (int j = i+1 ; j < topoOrderedConcepts.size() ; j++) {
				IConcept jConcept = topoOrderedConcepts.get(j);
				if (conceptLattice.isA(iConcept, jConcept)) {
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
