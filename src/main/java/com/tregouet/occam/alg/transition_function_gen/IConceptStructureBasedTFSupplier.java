package com.tregouet.occam.alg.transition_function_gen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.data.abstract_machines.functions.ISetOfRelatedTransFunctions;
import com.tregouet.occam.data.concepts.IClassification;
import com.tregouet.occam.data.concepts.IConcept;

public interface IConceptStructureBasedTFSupplier extends ITransitionFunctionSupplier, Iterator<ISetOfRelatedTransFunctions> {
	
	public static String getDefinitionOfObjects(Map<IConcept, String> singletonConceptToName) {
		StringBuilder sB = new StringBuilder();
		String newLine = System.lineSeparator();
		sB.append("*** DEFINITION OF OBJECTS ***" + newLine + newLine);
		List<IConcept> singletonConcepts = new ArrayList<>(singletonConceptToName.keySet());
		for (int i = 0 ; i < singletonConcepts.size() ; i++) {
			IConcept singleton = singletonConcepts.get(i);
			sB.append("**Object " + singletonConceptToName.get(singleton) + " :" + newLine);
			sB.append(singleton.toString());
			if (i < singletonConcepts.size() - 1)
				sB.append(newLine + newLine);
		}
		return sB.toString();
	}
	
	String getDefinitionOfObjects();
	
	IClassification getOptimalClassification();

}
