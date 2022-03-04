package com.tregouet.occam.alg.transition_function_gen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.data.abstract_machines.automatons.IIsomorphicAutomatons;
import com.tregouet.occam.data.denotations.IConcept;

public interface IStructureBasedTFSupplier extends ITransitionFunctionSupplier, Iterator<IIsomorphicAutomatons> {
	
	public static String getObjectsDenotationsAsString(Map<IConcept, String> objDenotationSetToName) {
		StringBuilder sB = new StringBuilder();
		String newLine = System.lineSeparator();
		sB.append("*** OBJECTS DENOTATIONS ***" + newLine + newLine);
		List<IConcept> objDenotationSets = new ArrayList<>(objDenotationSetToName.keySet());
		for (int i = 0 ; i < objDenotationSets.size() ; i++) {
			IConcept objDenotationSet = objDenotationSets.get(i);
			sB.append("**Object " + objDenotationSetToName.get(objDenotationSet) + " :" + newLine);
			sB.append(objDenotationSet.toString());
			if (i < objDenotationSets.size() - 1)
				sB.append(newLine + newLine);
		}
		return sB.toString();
	}
	
	String getObjectDenotationsAsString();

}
