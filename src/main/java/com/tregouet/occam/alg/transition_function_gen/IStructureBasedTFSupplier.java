package com.tregouet.occam.alg.transition_function_gen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.data.automata.machines.IIsomorphicAutomatons;
import com.tregouet.occam.data.preconcepts.IPreconcept;

public interface IStructureBasedTFSupplier extends ITransitionFunctionSupplier, Iterator<IIsomorphicAutomatons> {
	
	public static String getObjectsDenotationsAsString(Map<IPreconcept, String> objDenotationSetToName) {
		StringBuilder sB = new StringBuilder();
		String newLine = System.lineSeparator();
		sB.append("*** OBJECTS DENOTATIONS ***" + newLine + newLine);
		List<IPreconcept> objDenotationSets = new ArrayList<>(objDenotationSetToName.keySet());
		for (int i = 0 ; i < objDenotationSets.size() ; i++) {
			IPreconcept objDenotationSet = objDenotationSets.get(i);
			sB.append("**Object " + objDenotationSetToName.get(objDenotationSet) + " :" + newLine);
			sB.append(objDenotationSet.toString());
			if (i < objDenotationSets.size() - 1)
				sB.append(newLine + newLine);
		}
		return sB.toString();
	}
	
	String getObjectDenotationsAsString();

}
