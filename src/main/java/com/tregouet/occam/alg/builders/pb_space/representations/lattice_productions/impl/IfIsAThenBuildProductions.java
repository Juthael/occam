package com.tregouet.occam.alg.builders.pb_space.representations.lattice_productions.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.lattice_productions.ProductionBuilder;
import com.tregouet.occam.alg.builders.pb_space.representations.lattice_productions.from_denotations.ProdBuilderFromDenotations;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.productions.IContextualizedProduction;

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
			ProdBuilderFromDenotations builder = ProductionBuilder.prodBuilderFromDenotations().setUp(iConcept);
			for (int j = i + 1; j < topoOrderedConcepts.size(); j++) {
				IConcept jConcept = topoOrderedConcepts.get(j);
				if (conceptLattice.isA(iConcept, jConcept)) {
					for (IDenotation source : iConcept.getDenotations()) {
						for (IDenotation target : jConcept.getDenotations()) {
							Set<IContextualizedProduction> ijDenotationsProds = builder.apply(source, target);
							productions.addAll(ijDenotationsProds);
						}
					}
				}
			}
		}
		return productions;
	}

}
