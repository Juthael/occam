package com.tregouet.occam.alg.builders.pb_space.concept_lattices.utils;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.problem_space.states.classifications.concepts.IConcept;
import com.tregouet.occam.data.problem_space.states.classifications.concepts.denotations.IDenotation;

public interface MarkRedundantDenotations {

	public static void of(IConcept concept) {
		List<IDenotation> denotationList = new ArrayList<>(concept.getDenotations());
		for (int i = 0; i < denotationList.size() - 1; i++) {
			IDenotation iDenotation = denotationList.get(i);
			if (!iDenotation.isRedundant()) {
				for (int j = i + 1; j < denotationList.size(); j++) {
					IDenotation jDenotation = denotationList.get(j);
					if (!jDenotation.isRedundant()) {
						Integer comparisonResult = iDenotation.compareTo(jDenotation);
						if (comparisonResult != null) {
							if (comparisonResult < 0)
								jDenotation.markAsRedundant();
							if (comparisonResult > 0)
								iDenotation.markAsRedundant();
						}
					}
				}
			}
		}
	}

}
