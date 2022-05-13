package com.tregouet.occam.alg.builders.pb_space.representations.productions.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.productions.ProductionBuilder;
import com.tregouet.occam.data.representations.concepts.IConcept;
import com.tregouet.occam.data.representations.concepts.IConceptLattice;
import com.tregouet.occam.data.representations.concepts.denotations.IDenotation;
import com.tregouet.occam.data.representations.transitions.productions.IContextualizedProduction;

public class IfIsAThenBuildProductions implements ProductionBuilder {

	public static final IfIsAThenBuildProductions INSTANCE = new IfIsAThenBuildProductions();

	public IfIsAThenBuildProductions() {
	}

	@Override
	public Set<IContextualizedProduction> apply(IConceptLattice conceptLattice) {
		Set<IContextualizedProduction> productions = new HashSet<>();
		List<IConcept> topoOrderedConcepts = conceptLattice.getTopologicalSorting();
		for (int i = 0; i < topoOrderedConcepts.size() - 1; i++) {
			IConcept iConcept = topoOrderedConcepts.get(i);
			for (int j = i + 1; j < topoOrderedConcepts.size(); j++) {
				IConcept jConcept = topoOrderedConcepts.get(j);
				if (conceptLattice.isA(iConcept, jConcept)) {
					for (IDenotation source : iConcept.getDenotations()) {
						for (IDenotation target : jConcept.getDenotations()) {
							Set<IContextualizedProduction> ijDenotationsProds = ProductionBuilder
									.prodBuilderFromDenotations().apply(source, target);
							productions.addAll(ijDenotationsProds);
						}
					}
				}
			}
		}
		return productions;
	}

}
