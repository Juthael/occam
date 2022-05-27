package com.tregouet.occam.alg.builders.pb_space.representations.productions.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tregouet.occam.alg.builders.pb_space.representations.productions.ProductionBuilder;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.problem_space.states.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.concepts.IConceptLattice;
import com.tregouet.occam.data.problem_space.states.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.transitions.productions.IContextualizedProduction;

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
							if (denotationPairIsValid(source, iConcept, target, jConcept)) {
								Set<IContextualizedProduction> ijDenotationsProds = ProductionBuilder
										.prodBuilderFromDenotations().apply(source, target);
								productions.addAll(ijDenotationsProds);
							}
						}
					}
				}
			}
		}
		return productions;
	}
	
	private boolean denotationPairIsValid(IDenotation source, IConcept sourceConcept, IDenotation target, IConcept targetConcept) {
		if (!target.isRedundant())
			return true;
		IConstruct sourceConstruct = (IConstruct) source;
		IConstruct targetConstruct = (IConstruct) target;
		if (sourceConstruct.equals(targetConstruct))
			return true;
		else return (!sourceConceptAlreadyContainsTargetConstruct(sourceConcept, targetConstruct));
	}
	
	private boolean sourceConceptAlreadyContainsTargetConstruct(IConcept sourceConcept, IConstruct target) {
		Set<IConstruct> sourceRedundantConstructs = new HashSet<>();
		for (IDenotation redundantDenotation : sourceConcept.getRedundantDenotations())
			sourceRedundantConstructs.add((IConstruct) redundantDenotation);
		if (sourceRedundantConstructs.contains(target))
			return true;
		return false;
	}

}
