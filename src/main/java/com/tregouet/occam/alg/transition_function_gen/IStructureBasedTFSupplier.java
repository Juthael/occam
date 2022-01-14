package com.tregouet.occam.alg.transition_function_gen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tregouet.occam.data.abstract_machines.functions.IIsomorphicTransFunctions;
import com.tregouet.occam.data.denotations.IDenotationSet;

public interface IStructureBasedTFSupplier extends ITransitionFunctionSupplier, Iterator<IIsomorphicTransFunctions> {
	
	public static String getObjectsDenotationsAsString(Map<IDenotationSet, String> objDenotationSetToName) {
		StringBuilder sB = new StringBuilder();
		String newLine = System.lineSeparator();
		sB.append("*** OBJECTS DENOTATIONS ***" + newLine + newLine);
		List<IDenotationSet> objDenotationSets = new ArrayList<>(objDenotationSetToName.keySet());
		for (int i = 0 ; i < objDenotationSets.size() ; i++) {
			IDenotationSet objDenotationSet = objDenotationSets.get(i);
			sB.append("**Object " + objDenotationSetToName.get(objDenotationSet) + " :" + newLine);
			sB.append(objDenotationSet.toString());
			if (i < objDenotationSets.size() - 1)
				sB.append(newLine + newLine);
		}
		return sB.toString();
	}
	
	String getObjectDenotationsAsString();

}
