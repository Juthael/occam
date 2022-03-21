package com.tregouet.occam.alg.builders.preconcepts.utils;

import java.util.ArrayList;
import java.util.List;

import com.tregouet.occam.data.preconcepts.IDenotation;
import com.tregouet.occam.data.preconcepts.IPreconcept;

public interface MarkRedundantDenotations {
	
	public static void of(IPreconcept preconcept) {
		List<IDenotation> denotationList = new ArrayList<>(preconcept.getDenotations());
		for (int i = 0 ; i < denotationList.size() - 1 ; i++) {
			IDenotation iDenotation = denotationList.get(i);
			if (!iDenotation.isRedundant()) {
				for (int j = i + 1 ; j < denotationList.size() ; j++) {
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
