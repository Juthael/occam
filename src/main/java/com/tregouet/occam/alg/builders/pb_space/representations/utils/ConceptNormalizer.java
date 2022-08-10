package com.tregouet.occam.alg.builders.pb_space.representations.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.data.logical_structures.languages.alphabets.AVariable;
import com.tregouet.occam.data.logical_structures.languages.alphabets.ISymbol;
import com.tregouet.occam.data.logical_structures.languages.words.construct.IConstruct;
import com.tregouet.occam.data.logical_structures.languages.words.construct.impl.Construct;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.ConceptType;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.impl.Concept;

public interface ConceptNormalizer {

	public static IConcept normalize(IConcept concept, Map<AVariable, AVariable> replaced2Substitute) {
		if (concept.type() == ConceptType.PARTICULAR || concept.type() == ConceptType.ONTOLOGICAL_COMMITMENT)
			return concept;
		int nbOfDenotations = concept.getDenotations().size();
		IConstruct[] normalizedConstructs = new IConstruct[nbOfDenotations];
		boolean[] redundant = new boolean[nbOfDenotations];
		int idx = 0;
		for (IDenotation denotation : concept.getDenotations()) {
			if (denotation.isAbstract()) {
				List<ISymbol> symbolList = new ArrayList<>();
				for (ISymbol symbol : denotation.asList()) {
					if (symbol instanceof AVariable) {
						AVariable var = (AVariable) symbol;
						AVariable substituteVar = replaced2Substitute.get(var);
						if (substituteVar == null)
							symbolList.add(var);
						else symbolList.add(substituteVar);
					}
					else symbolList.add(symbol);
				}
				normalizedConstructs[idx] = new Construct(symbolList);
			}
			else normalizedConstructs[idx] = new Construct(denotation);
			redundant[idx] = denotation.isRedundant();
			idx++;
		}
		IConcept normalizedConcept = new Concept(normalizedConstructs, redundant, concept.getMaxExtentIDs(), concept.iD());
		normalizedConcept.setType(concept.type());
		return normalizedConcept;
	}

}
